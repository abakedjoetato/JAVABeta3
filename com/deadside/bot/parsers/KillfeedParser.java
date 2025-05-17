package com.deadside.bot.parsers;

import com.deadside.bot.db.models.Bounty;
import com.deadside.bot.db.models.GameServer;
import com.deadside.bot.db.models.KillRecord;
import com.deadside.bot.db.models.Player;
import com.deadside.bot.db.models.LinkedPlayer;
import com.deadside.bot.db.repositories.BountyRepository;
import com.deadside.bot.db.repositories.KillRecordRepository;
import com.deadside.bot.db.repositories.PlayerRepository;
import com.deadside.bot.db.repositories.LinkedPlayerRepository;
import com.deadside.bot.sftp.SftpManager;
import com.deadside.bot.utils.EmbedUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for Deadside killfeed CSV files
 */
public class KillfeedParser {
    private static final Logger logger = LoggerFactory.getLogger(KillfeedParser.class);
    private final SftpManager sftpManager;
    private final KillRecordRepository killRecordRepository;
    private final PlayerRepository playerRepository;
    private final LinkedPlayerRepository linkedPlayerRepository;
    private final BountyRepository bountyRepository;
    private final JDA jda;
    
    // CSV format: "1970/01/01-00:00:00","PlayerName1","killed","PlayerName2","with","WeaponName","from","100m"
    private static final Pattern CSV_PATTERN = Pattern.compile(
            "\"([^\"]+)\",\"([^\"]+)\",\"([^\"]+)\",\"([^\"]+)\",\"([^\"]+)\",\"([^\"]+)\",\"([^\"]+)\",\"(\\d+)m\""
    );
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
    
    // Emerald color for bounty notifications
    private static final Color EMERALD_COLOR = new Color(46, 204, 113);
    
    public KillfeedParser(JDA jda) {
        this.jda = jda;
        this.sftpManager = new SftpManager();
        this.killRecordRepository = new KillRecordRepository();
        this.playerRepository = new PlayerRepository();
        this.linkedPlayerRepository = new LinkedPlayerRepository();
        this.bountyRepository = new BountyRepository();
    }
    
    /**
     * Process killfeed for a server
     * @param server The game server to process
     * @return Number of new kill records processed
     */
    public int processServer(GameServer server) {
        try {
            TextChannel killfeedChannel = jda.getTextChannelById(server.getKillfeedChannelId());
            if (killfeedChannel == null) {
                logger.warn("Killfeed channel not found for server: {}", server.getName());
                return 0;
            }
            
            // Get CSV files
            List<String> files = sftpManager.getKillfeedFiles(server);
            if (files.isEmpty()) {
                logger.warn("No killfeed files found for server: {}", server.getName());
                return 0;
            }
            
            // Sort files by name (should be date-based)
            Collections.sort(files);
            
            String lastProcessedFile = server.getLastProcessedKillfeedFile();
            long lastProcessedLine = server.getLastProcessedKillfeedLine();
            
            // If no file has been processed yet, start with the newest file
            if (lastProcessedFile.isEmpty()) {
                lastProcessedFile = files.get(files.size() - 1);
                lastProcessedLine = -1;
            }
            
            // Check if we need to move to a newer file
            int fileIndex = files.indexOf(lastProcessedFile);
            if (fileIndex < 0) {
                // File no longer exists, start with the newest file
                lastProcessedFile = files.get(files.size() - 1);
                lastProcessedLine = -1;
            } else if (fileIndex < files.size() - 1) {
                // Newer files available, move to the next one
                lastProcessedFile = files.get(fileIndex + 1);
                lastProcessedLine = -1;
            }
            
            // Process the file
            String fileContent = sftpManager.readKillfeedFile(server, lastProcessedFile);
            if (fileContent.isEmpty()) {
                logger.warn("Empty or unreadable killfeed file: {} for server: {}", 
                        lastProcessedFile, server.getName());
                return 0;
            }
            
            String[] lines = fileContent.split("\n");
            List<KillRecord> newRecords = new ArrayList<>();
            int processedKills = 0;
            
            // Process each line after the last processed line
            for (int i = 0; i < lines.length; i++) {
                if (i <= lastProcessedLine) continue;
                
                String line = lines[i].trim();
                if (line.isEmpty()) continue;
                
                KillRecord killRecord = parseKillRecord(line, server);
                if (killRecord != null) {
                    newRecords.add(killRecord);
                    processedKills++;
                    
                    // Update player stats
                    updatePlayerStats(killRecord);
                    
                    // Send to Discord channel
                    sendKillfeedMessage(killfeedChannel, killRecord);
                }
                
                lastProcessedLine = i;
            }
            
            // Save all new records to database
            if (!newRecords.isEmpty()) {
                killRecordRepository.saveAll(newRecords);
            }
            
            // Update server progress
            server.updateKillfeedProgress(lastProcessedFile, lastProcessedLine);
            
            logger.info("Processed {} new kills for server: {}", processedKills, server.getName());
            return processedKills;
        } catch (Exception e) {
            logger.error("Error processing killfeed for server: {}", server.getName(), e);
            return 0;
        }
    }
    
    /**
     * Parse a CSV line into a KillRecord
     */
    private KillRecord parseKillRecord(String line, GameServer server) {
        Matcher matcher = CSV_PATTERN.matcher(line);
        if (!matcher.matches()) {
            logger.warn("Killfeed line does not match expected format: {}", line);
            return null;
        }
        
        try {
            String timestamp = matcher.group(1);
            String killer = matcher.group(2);
            String action = matcher.group(3);
            String victim = matcher.group(4);
            String weapon = matcher.group(6);
            String distanceStr = matcher.group(8);
            
            if (!action.equals("killed")) {
                logger.warn("Unknown killfeed action: {} in line: {}", action, line);
                return null;
            }
            
            long distance = Long.parseLong(distanceStr);
            long timeMs = DATE_FORMAT.parse(timestamp).getTime();
            
            return new KillRecord(
                    server.getGuildId(),
                    server.getName(),
                    killer,
                    victim,
                    weapon,
                    distance,
                    timeMs,
                    line
            );
        } catch (ParseException e) {
            logger.error("Error parsing killfeed timestamp in line: {}", line, e);
            return null;
        } catch (NumberFormatException e) {
            logger.error("Error parsing killfeed distance in line: {}", line, e);
            return null;
        } catch (Exception e) {
            logger.error("Error parsing killfeed line: {}", line, e);
            return null;
        }
    }
    
    /**
     * Update player statistics from a kill record
     */
    private void updatePlayerStats(KillRecord record) {
        try {
            // Find or create killer player
            Player killer = playerRepository.findByName(record.getKiller());
            if (killer == null) {
                // Create new player with a generated ID based on name
                killer = new Player(record.getKiller().toLowerCase().replace(" ", "_") + "_id", record.getKiller());
                playerRepository.save(killer);
            }
            
            // Find or create victim player
            Player victim = playerRepository.findByName(record.getVictim());
            if (victim == null) {
                // Create new player with a generated ID based on name
                victim = new Player(record.getVictim().toLowerCase().replace(" ", "_") + "_id", record.getVictim());
                playerRepository.save(victim);
            }
            
            // Update killer stats
            killer.addKill();
            playerRepository.save(killer);
            
            // Update victim stats
            victim.addDeath();
            playerRepository.save(victim);
            
            // Track weapon stats
            if (killer.getMostUsedWeapon().equals(record.getWeapon())) {
                killer.setMostUsedWeaponKills(killer.getMostUsedWeaponKills() + 1);
            } else if (killer.getMostUsedWeaponKills() == 0) {
                killer.setMostUsedWeapon(record.getWeapon());
                killer.setMostUsedWeaponKills(1);
            }
            // More weapon tracking logic would go here
            
            // Track victim stats
            if (killer.getMostKilledPlayer().equals(record.getVictim())) {
                killer.setMostKilledPlayerCount(killer.getMostKilledPlayerCount() + 1);
            } else if (killer.getMostKilledPlayerCount() == 0) {
                killer.setMostKilledPlayer(record.getVictim());
                killer.setMostKilledPlayerCount(1);
            }
            // More victim tracking logic would go here
            
            // Track killer stats for victim
            if (victim.getKilledByMost().equals(record.getKiller())) {
                victim.setKilledByMostCount(victim.getKilledByMostCount() + 1);
            } else if (victim.getKilledByMostCount() == 0) {
                victim.setKilledByMost(record.getKiller());
                victim.setKilledByMostCount(1);
            }
            // More killer tracking logic would go here
            
            // Process any bounties for this kill
            processBounties(record);
            
        } catch (Exception e) {
            logger.error("Error updating player stats for kill record: {} -> {}", 
                    record.getKiller(), record.getVictim(), e);
        }
    }
    
    /**
     * Process bounties for a kill record
     * Check if the victim had any bounties and if so, reward the killer
     */
    private void processBounties(KillRecord record) {
        try {
            String victimName = record.getVictim();
            String killerName = record.getKiller();
            long guildId = record.getGuildId();
            
            // Get all active bounties for this victim
            List<Bounty> bounties = bountyRepository.findActiveByTargetName(victimName);
            if (bounties.isEmpty()) {
                return; // No bounties to process
            }
            
            // Get the game server info for notification channel
            List<GameServer> servers = new ArrayList<>();
            for (Bounty bounty : bounties) {
                if (bounty.getGuildId() == guildId) {
                    servers = new com.deadside.bot.db.repositories.GameServerRepository().findByGuildId(guildId);
                    break;
                }
            }
            
            if (servers.isEmpty()) {
                logger.warn("No server found for guild ID {} when processing bounties", guildId);
                return;
            }
            
            GameServer server = servers.get(0);
            Guild guild = jda.getGuildById(guildId);
            
            if (guild == null) {
                logger.warn("Guild not found for ID {} when processing bounties", guildId);
                return;
            }
            
            // Only process if bounty channel is configured
            if (server.getBountyChannelId() == 0) {
                return;
            }
            
            TextChannel bountyChannel = guild.getTextChannelById(server.getBountyChannelId());
            if (bountyChannel == null) {
                logger.warn("Bounty channel not found for server {}", server.getName());
                return;
            }
            
            // Track total earnings
            long totalEarnings = 0;
            List<Bounty> completedBounties = new ArrayList<>();
            
            // Process each bounty
            for (Bounty bounty : bounties) {
                // Mark bounty as completed
                if (bountyRepository.completeBounty(bounty, killerName)) {
                    completedBounties.add(bounty);
                    totalEarnings += bounty.getAmount();
                }
            }
            
            if (completedBounties.isEmpty()) {
                return; // No bounties were completed
            }
            
            // Reward the killer if they are linked to a Discord account
            LinkedPlayer linkedKiller = linkedPlayerRepository.findByGameName(killerName);
            if (linkedKiller != null) {
                long discordId = linkedKiller.getDiscordId();
                // Add coins to the player's balance
                new PlayerRepository().addCoins(discordId, guildId, totalEarnings);
                
                // Send notification to bounty channel
                sendBountyCompletionNotification(bountyChannel, killerName, victimName, 
                        completedBounties, totalEarnings, discordId);
            } else {
                // Still send notification but mention no linked account
                sendBountyCompletionNotification(bountyChannel, killerName, victimName, 
                        completedBounties, totalEarnings, 0);
            }
            
        } catch (Exception e) {
            logger.error("Error processing bounties for kill record: {}", record, e);
        }
    }
    
    /**
     * Send notification about completed bounties
     */
    private void sendBountyCompletionNotification(
            TextChannel channel, String killer, String victim, 
            List<Bounty> bounties, long totalEarnings, long killerId) {
        
        StringBuilder description = new StringBuilder();
        description.append("**").append(killer).append("** has eliminated **")
                .append(victim).append("** and claimed ").append(totalEarnings)
                .append(" coins in bounty rewards!\n\n");
        
        // List each bounty that was completed
        description.append("__Claimed Bounties:__\n");
        for (Bounty bounty : bounties) {
            if (bounty.isAiGenerated()) {
                description.append("• ")
                        .append(bounty.getAmount())
                        .append(" coins (AI-generated bounty)\n");
            } else {
                description.append("• ")
                        .append(bounty.getAmount())
                        .append(" coins (posted by <@")
                        .append(bounty.getIssuerId())
                        .append(">)\n");
            }
        }
        
        // Note if coins were awarded
        if (killerId > 0) {
            description.append("\n💰 **").append(totalEarnings)
                    .append(" coins** have been added to <@")
                    .append(killerId).append(">'s balance.");
        } else {
            description.append("\n⚠️ **").append(killer)
                    .append("** is not linked to a Discord account. Link your account with `/link` to claim future bounties!");
        }
        
        channel.sendMessageEmbeds(EmbedUtils.customEmbed(
                "🏆 Bounty Claimed!",
                description.toString(),
                EMERALD_COLOR
        )).queue();
    }
    
    /**
     * Send a killfeed message to Discord
     */
    private void sendKillfeedMessage(TextChannel channel, KillRecord record) {
        if (channel == null) return;
        
        String title = "🎯 Killfeed";
        String description = String.format(
                "**%s** killed **%s**\n" +
                "Weapon: **%s**\n" +
                "Distance: **%d m**\n" +
                "Time: <t:%d:R>",
                record.getKiller(),
                record.getVictim(),
                record.getWeapon(),
                record.getDistance(),
                record.getTimestamp() / 1000
        );
        
        channel.sendMessageEmbeds(EmbedUtils.killfeedEmbed(title, description)).queue();
    }
}
