package com.deadside.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Advanced embed designs for killfeed and leaderboard displays
 * Implements Phase 6 of the embed modernization with premium-grade UI
 */
public class AdvancedEmbeds {
    private static final Random random = new Random();
    
    // Color scheme for advanced embeds
    private static final Color EMERALD_GREEN = EmbedUtils.EMERALD_GREEN;
    private static final Color DARK_GRAY = EmbedUtils.DARK_GRAY;
    private static final Color GOLD = new Color(212, 175, 55);
    private static final Color SILVER = new Color(192, 192, 192);
    private static final Color BRONZE = new Color(205, 127, 50);
    
    // Standard footer text
    private static final String STANDARD_FOOTER = "Powered By Discord.gg/EmeraldServers";
    
    /**
     * Create an advanced killfeed embed with dynamic styling based on kill context
     * 
     * @param killer The player who made the kill
     * @param victim The player who was killed
     * @param weapon The weapon used
     * @param distance The distance of the kill in meters
     * @param isBounty Whether this was a bounty kill
     * @param killStreak Optional kill streak count (0 for no streak)
     * @return A visually enhanced embed for the killfeed
     */
    public static MessageEmbed advancedKillfeedEmbed(String killer, String victim, String weapon, 
                                                  int distance, boolean isBounty, int killStreak) {
        // Get dynamic title and description
        String title = DynamicTitles.getKillfeedTitle();
        String description = DynamicTitles.getKillfeedDescription(killer, victim, weapon, distance);
        
        // Select color based on context
        Color embedColor = EMERALD_GREEN;
        if (isBounty) {
            embedColor = GOLD;
            title = DynamicTitles.getBountyTitle();
            description = killer + " collected the bounty on " + victim + "'s head";
        } else if (killStreak > 2) {
            // Darker green for kill streaks
            embedColor = new Color(0, 140, 80);
        }
        
        // Build the embed with enhanced visuals
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(embedColor)
                .setThumbnail(isBounty ? EmbedUtils.BOUNTY_ICON : EmbedUtils.KILLFEED_ICON)
                .addField("Killer", "**" + killer + "**" + (killStreak > 1 ? " 🔥" + killStreak : ""), true)
                .addField("Victim", "**" + victim + "**", true)
                .addField("Weapon", weapon, true)
                .addField("Distance", distance + "m", true);
        
        // Add contextual fields based on kill type
        if (isBounty) {
            embed.addField("Bounty", "Target Eliminated", false);
        }
        
        if (killStreak > 2) {
            embed.addField("Streak", killer + " is on a " + killStreak + " kill streak!", false);
        }
        
        // Add weapon effectiveness rating based on distance
        String effectivenessRating = getWeaponEffectivenessRating(weapon, distance);
        if (effectivenessRating != null) {
            embed.addField("Rating", effectivenessRating, false);
        }
        
        // Add timestamp and footer
        embed.setFooter(STANDARD_FOOTER)
             .setTimestamp(Instant.now());
        
        return embed.build();
    }
    
    /**
     * Create an advanced leaderboard embed with pagination support
     * 
     * @param title Optional custom title (or null to use dynamic title)
     * @param description Optional custom description (or null to use dynamic description)
     * @param playerData List of player data objects to display
     * @param page Current page number (0-based)
     * @param totalPages Total number of pages
     * @return A visually enhanced embed for leaderboards
     */
    public static MessageEmbed advancedLeaderboardEmbed(String title, String description,
                                                     List<PlayerData> playerData, int page, int totalPages) {
        // Use dynamic titles and descriptions if not provided
        if (title == null) {
            title = DynamicTitles.getLeaderboardTitle();
        }
        
        if (description == null) {
            description = DynamicTitles.getLeaderboardDescription();
        }
        
        // Add page information to description if multiple pages
        if (totalPages > 1) {
            description += "\nPage " + (page + 1) + " of " + totalPages;
        }
        
        // Build the embed with enhanced visuals
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(EMERALD_GREEN)
                .setThumbnail(EmbedUtils.WEAPON_STATS_ICON);
        
        // Add player entries with rank indicators
        int startRank = page * 10;
        for (int i = 0; i < playerData.size() && i < 10; i++) {
            int rank = startRank + i + 1;
            PlayerData player = playerData.get(i);
            
            // Add rank emoji for top 3
            String rankPrefix = "";
            if (rank == 1) {
                rankPrefix = "🥇 ";
            } else if (rank == 2) {
                rankPrefix = "🥈 ";
            } else if (rank == 3) {
                rankPrefix = "🥉 ";
            } else {
                rankPrefix = "#" + rank + " ";
            }
            
            // Format the player entry with all stats
            String formattedEntry = rankPrefix + "**" + player.getName() + "**\n" +
                                   "K/D: " + formatKD(player.getKills(), player.getDeaths()) + " • " +
                                   "Kills: " + player.getKills() + " • " +
                                   "Deaths: " + player.getDeaths();
            
            // Add faction info if available
            if (player.getFaction() != null && !player.getFaction().isEmpty()) {
                formattedEntry += "\nFaction: " + player.getFaction();
            }
            
            // Add top weapon if available
            if (player.getTopWeapon() != null && !player.getTopWeapon().isEmpty()) {
                formattedEntry += "\nTop Weapon: " + player.getTopWeapon();
            }
            
            // Add the field with the player's entry
            embed.addField("", formattedEntry, false);
        }
        
        // Add timestamp and footer
        embed.setFooter(STANDARD_FOOTER)
             .setTimestamp(Instant.now());
        
        return embed.build();
    }
    
    /**
     * Create an advanced suicide embed with thematic formatting
     * 
     * @param player The player who died
     * @param cause The cause of death
     * @return A visually enhanced embed for suicide events
     */
    public static MessageEmbed advancedSuicideEmbed(String player, String cause) {
        // Normalize suicide by relocation
        boolean isMenuSuicide = false;
        if (cause.equals("suicide_by_relocation")) {
            cause = "Menu Suicide";
            isMenuSuicide = true;
        } else {
            cause = cause.replace("_", " ");
        }
        
        // Get dynamic title and description
        String title = DynamicTitles.getSuicideTitle();
        String description = isMenuSuicide ? 
            player + " returned to the void (Menu Suicide)" : 
            DynamicTitles.getSuicideDescription(player);
        
        // Build the enhanced embed
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(DARK_GRAY)
                .setThumbnail(EmbedUtils.HELICRASH_ICON)
                .addField("Player", player, true)
                .addField("Cause", cause, true)
                .addField("Location", "Unknown", true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create an advanced falling death embed with thematic formatting
     * 
     * @param player The player who died
     * @param height The height of the fall in meters
     * @return A visually enhanced embed for falling death events
     */
    public static MessageEmbed advancedFallingDeathEmbed(String player, int height) {
        // Get dynamic title and description
        String title = DynamicTitles.getFallingTitle();
        String description = DynamicTitles.getFallingDescription(player, height);
        
        // Calculate severity based on height
        String severity = "Fatal";
        if (height > 30) {
            severity = "Catastrophic";
        } else if (height > 20) {
            severity = "Devastating";
        } else if (height > 10) {
            severity = "Severe";
        }
        
        // Build the enhanced embed
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(DARK_GRAY)
                .setThumbnail(EmbedUtils.HELICRASH_ICON)
                .addField("Player", player, true)
                .addField("Cause", "Falling damage", true)
                .addField("Height", height + "m", true)
                .addField("Severity", severity, true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Format a K/D ratio with color coding based on performance
     * 
     * @param kills Number of kills
     * @param deaths Number of deaths
     * @return Formatted K/D ratio string
     */
    private static String formatKD(int kills, int deaths) {
        double kd = deaths > 0 ? (double) kills / deaths : kills;
        String kdFormatted = String.format("%.2f", kd);
        
        // Color code based on K/D performance
        if (kd >= 3.0) {
            return "**" + kdFormatted + "**"; // Bold for excellent K/D
        } else if (kd >= 1.5) {
            return kdFormatted; // Normal for good K/D
        } else {
            return kdFormatted; // Normal for average or poor K/D
        }
    }
    
    /**
     * Get a weapon effectiveness rating based on weapon type and distance
     * 
     * @param weapon The weapon used
     * @param distance The distance of the kill
     * @return A rating string or null if no rating
     */
    private static String getWeaponEffectivenessRating(String weapon, int distance) {
        // Simplified weapon categories
        boolean isSniper = weapon.toLowerCase().contains("sniper") || 
                           weapon.equalsIgnoreCase("SVD") || 
                           weapon.equalsIgnoreCase("Mosin");
                           
        boolean isShotgun = weapon.toLowerCase().contains("shotgun") || 
                            weapon.equalsIgnoreCase("12ga") || 
                            weapon.equalsIgnoreCase("KS-23");
                            
        boolean isPistol = weapon.toLowerCase().contains("pistol") || 
                          weapon.equalsIgnoreCase("Deagle") || 
                          weapon.equalsIgnoreCase("Glock");
        
        // Rate based on weapon category and distance
        if (isSniper && distance > 200) {
            return "Exceptional long-range shot";
        } else if (isSniper && distance > 100) {
            return "Solid sniper work";
        } else if (isShotgun && distance > 50) {
            return "Impressive shotgun range";
        } else if (isShotgun && distance < 10) {
            return "Close-quarters specialist";
        } else if (isPistol && distance > 50) {
            return "Remarkable pistol accuracy";
        } else if (distance > 300) {
            return "Extreme range elimination";
        }
        
        return null; // No special rating
    }
    
    /**
     * Player data class for leaderboard entries
     */
    public static class PlayerData {
        private final String name;
        private final int kills;
        private final int deaths;
        private final String faction;
        private final String topWeapon;
        
        public PlayerData(String name, int kills, int deaths, String faction, String topWeapon) {
            this.name = name;
            this.kills = kills;
            this.deaths = deaths;
            this.faction = faction;
            this.topWeapon = topWeapon;
        }
        
        public String getName() {
            return name;
        }
        
        public int getKills() {
            return kills;
        }
        
        public int getDeaths() {
            return deaths;
        }
        
        public String getFaction() {
            return faction;
        }
        
        public String getTopWeapon() {
            return topWeapon;
        }
    }
}