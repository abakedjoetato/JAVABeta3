package com.deadside.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Utility class for creating modern Discord message embeds
 * Uses Deadside-themed emerald color palette with post-apocalyptic styling
 */
public class EmbedUtils {
    // Deadside themed color palette - Updated for modern design standards
    public static final Color EMERALD_GREEN = new Color(80, 200, 120);      // Primary emerald green (#50C878)
    public static final Color DARK_GRAY = new Color(45, 45, 45);           // Dark background
    public static final Color DARKER_GRAY = new Color(25, 25, 25);         // Even darker background
    public static final Color RUST_ACCENT = new Color(168, 85, 62);         // Rust accent
    public static final Color STEEL_BLUE = new Color(70, 97, 124);          // Steel blue accent
    
    // Standard colors for different embed types
    private static final Color SUCCESS_COLOR = EMERALD_GREEN;               // Success green
    private static final Color ERROR_COLOR = new Color(189, 59, 59);        // Desaturated error red
    private static final Color INFO_COLOR = STEEL_BLUE;                     // Info steel blue
    private static final Color WARNING_COLOR = new Color(209, 139, 71);     // Desaturated warning orange
    
    // Standard images/logos
    private static final String MAIN_LOGO = "https://i.imgur.com/Mainlogo.png";
    public static final String KILLFEED_ICON = "attachment://Killfeed.png";
    public static final String BOUNTY_ICON = "attachment://Bounty.png";
    public static final String MISSION_ICON = "attachment://Mission.png";
    public static final String FACTION_ICON = "attachment://Faction.png";
    public static final String AIRDROP_ICON = "attachment://Airdrop.png";
    public static final String TRADER_ICON = "attachment://Trader.png";
    public static final String CONNECTIONS_ICON = "attachment://Connections.png";
    public static final String WEAPON_STATS_ICON = "attachment://WeaponStats.png";
    public static final String HELICRASH_ICON = "attachment://Helicrash.png";
    
    // Standard footer text
    public static final String STANDARD_FOOTER = "Powered By Discord.gg/EmeraldServers";
    
    // Random generator for dynamic titles and messages
    private static final Random random = new Random();
    
    /**
     * Create a modern success embed
     */
    public static MessageEmbed successEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(SUCCESS_COLOR)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern error embed
     */
    public static MessageEmbed errorEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(ERROR_COLOR)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern info embed
     */
    public static MessageEmbed infoEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(INFO_COLOR)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern warning embed
     */
    public static MessageEmbed warningEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(WARNING_COLOR)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern custom colored embed
     */
    public static MessageEmbed customEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern custom colored embed with thumbnail
     */
    public static MessageEmbed customEmbedWithThumbnail(String title, String description, 
                                                       Color color, String thumbnailUrl) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setThumbnail(thumbnailUrl)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern player stats embed with themed styling
     */
    public static EmbedBuilder playerStatsEmbed(String playerName) {
        return new EmbedBuilder()
                .setTitle("Stats for " + playerName)
                .setColor(EMERALD_GREEN)
                .setThumbnail(WEAPON_STATS_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now());
    }
    
    /**
     * Dynamic title generator for killfeed embeds
     */
    private static String getRandomKillfeedTitle() {
        List<String> titles = Arrays.asList(
            "Elimination Confirmed",
            "No Survivors",
            "Precision Eliminated",
            "Target Neutralized",
            "Combat Report",
            "Deadside Elimination",
            "Hostile Encounter",
            "Lethal Engagement"
        );
        return titles.get(random.nextInt(titles.size()));
    }
    
    /**
     * Dynamic title generator for bounty kill embeds
     */
    private static String getRandomBountyTitle() {
        List<String> titles = Arrays.asList(
            "Contract Fulfilled",
            "Marked for Death",
            "Bounty Claimed",
            "Target Eliminated",
            "Blood Money Collected",
            "Hunter's Prize"
        );
        return titles.get(random.nextInt(titles.size()));
    }
    
    /**
     * Dynamic title generator for suicide embeds
     */
    private static String getRandomSuicideTitle() {
        List<String> titles = Arrays.asList(
            "Self-Termination Logged",
            "Silent Exit",
            "Final Decision",
            "Personal Ending",
            "Self-Inflicted End"
        );
        return titles.get(random.nextInt(titles.size()));
    }
    
    /**
     * Dynamic title generator for falling death embeds
     */
    private static String getRandomFallingTitle() {
        List<String> titles = Arrays.asList(
            "Terminal Velocity",
            "Gravity Claimed Another",
            "Fatal Descent",
            "The Ground Won",
            "Deadly Fall"
        );
        return titles.get(random.nextInt(titles.size()));
    }
    
    /**
     * Dynamic title generator for leaderboard embeds
     */
    private static String getRandomLeaderboardTitle() {
        List<String> titles = Arrays.asList(
            "Apex Predators",
            "Top Hunters of the Week",
            "Factions at War",
            "Deadside's Deadliest",
            "Survival Rankings",
            "Kill Hierarchy"
        );
        return titles.get(random.nextInt(titles.size()));
    }
    
    /**
     * Dynamic description generator for killfeed embeds
     */
    private static String getRandomKillfeedDescription(String killer, String victim, String weapon, int distance) {
        List<String> descriptions = Arrays.asList(
            killer + " removed " + victim + " at " + distance + "m with " + weapon,
            killer + " eliminated " + victim + " from " + distance + "m using " + weapon,
            killer + " neutralized " + victim + " with " + weapon + " (" + distance + "m)",
            "Clean shot by " + killer + ". " + victim + " never saw it coming.",
            victim + " fell to " + killer + "'s " + weapon + " at " + distance + " meters"
        );
        return descriptions.get(random.nextInt(descriptions.size()));
    }
    
    /**
     * Create a modern styled killfeed embed with dynamic titles
     */
    public static MessageEmbed killfeedEmbed(String killer, String victim, String weapon, int distance) {
        String title = getRandomKillfeedTitle();
        String description = getRandomKillfeedDescription(killer, victim, weapon, distance);
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(EMERALD_GREEN)
                .setThumbnail(KILLFEED_ICON)
                .addField("Killer", killer, true)
                .addField("Victim", victim, true)
                .addField("Weapon", weapon, true)
                .addField("Distance", distance + "m", true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled bounty killfeed embed
     */
    public static MessageEmbed bountyKillfeedEmbed(String killer, String victim, String weapon, int distance, int bountyAmount) {
        String title = getRandomBountyTitle();
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(killer + " collected the bounty on " + victim + "'s head")
                .setColor(new Color(212, 175, 55)) // Gold color for bounties
                .setThumbnail(BOUNTY_ICON)
                .addField("Hunter", killer, true)
                .addField("Target", victim, true)
                .addField("Weapon", weapon, true)
                .addField("Distance", distance + "m", true)
                .addField("Bounty Collected", bountyAmount + " credits", false)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled suicide embed
     */
    public static MessageEmbed suicideEmbed(String player, String cause) {
        String title = getRandomSuicideTitle();
        String description;
        
        // Normalize suicide by relocation
        if (cause.equals("suicide_by_relocation")) {
            cause = "Menu Suicide";
            description = player + " returned to the void (Menu Suicide)";
        } else {
            description = player + " ended their own journey (" + cause.replace("_", " ") + ")";
        }
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(DARK_GRAY)
                .addField("Player", player, true)
                .addField("Cause", cause.replace("_", " "), true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled falling death embed
     */
    public static MessageEmbed fallingDeathEmbed(String player, int height) {
        String title = getRandomFallingTitle();
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(player + " fell from a great height")
                .setColor(DARK_GRAY)
                .addField("Player", player, true)
                .addField("Cause", "Falling damage", true)
                .addField("Height", height + "m", true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled faction embed
     */
    public static MessageEmbed factionEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color != null ? color : EMERALD_GREEN)
                .setThumbnail(FACTION_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled economy embed
     */
    public static MessageEmbed economyEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(EMERALD_GREEN)
                .setThumbnail(TRADER_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled premium feature embed
     */
    public static MessageEmbed premiumEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(EMERALD_GREEN)
                .setThumbnail(MAIN_LOGO)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled server event embed
     */
    public static MessageEmbed eventEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(EMERALD_GREEN)
                .setThumbnail(MISSION_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled airdrop event embed
     */
    public static MessageEmbed airdropEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(STEEL_BLUE)
                .setThumbnail(AIRDROP_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled helicopter crash event embed
     */
    public static MessageEmbed helicrashEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(RUST_ACCENT)
                .setThumbnail(HELICRASH_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a modern styled leaderboard embed
     */
    public static EmbedBuilder leaderboardEmbed() {
        String title = getRandomLeaderboardTitle();
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription("Survivors ranked by raw efficiency:")
                .setColor(EMERALD_GREEN)
                .setThumbnail(WEAPON_STATS_ICON)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now());
    }
    
    /**
     * Create a modern styled connection log embed
     */
    public static MessageEmbed connectionLogEmbed(String player, boolean isJoining) {
        String title = isJoining ? "Player Connected" : "Player Disconnected";
        String description = isJoining ? 
                player + " entered the zone" : 
                player + " left the zone";
        
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(isJoining ? EMERALD_GREEN : DARK_GRAY)
                .setThumbnail(CONNECTIONS_ICON)
                .addField("Player", player, true)
                .addField("Status", isJoining ? "Online" : "Offline", true)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a default embed with standard Deadside styling (returns a built MessageEmbed)
     */
    public static MessageEmbed createDefaultEmbed() {
        return new EmbedBuilder()
                .setTitle("Deadside Bot")
                .setDescription("Deadside Discord Bot")
                .setColor(EMERALD_GREEN)
                .setThumbnail(MAIN_LOGO)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a default embed builder with standard Deadside styling
     * @return EmbedBuilder that can be further customized
     */
    public static EmbedBuilder createDefaultEmbedBuilder() {
        return new EmbedBuilder()
                .setColor(EMERALD_GREEN)
                .setFooter(STANDARD_FOOTER)
                .setTimestamp(Instant.now());
    }
}