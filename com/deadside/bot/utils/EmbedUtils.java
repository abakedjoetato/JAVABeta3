package com.deadside.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.time.Instant;

/**
 * Utility class for creating Discord message embeds
 * Uses Deadside-themed emerald color palette for consistency
 */
public class EmbedUtils {
    // Deadside themed color palette
    public static final Color DEADSIDE_PRIMARY = new Color(39, 174, 96);      // Primary emerald green
    public static final Color DEADSIDE_SECONDARY = new Color(46, 204, 113);   // Secondary emerald green
    public static final Color DEADSIDE_DARK = new Color(24, 106, 59);         // Darker emerald shade
    public static final Color DEADSIDE_LIGHT = new Color(88, 214, 141);       // Lighter emerald shade
    public static final Color DEADSIDE_ACCENT = new Color(26, 188, 156);      // Accent turquoise
    
    // Standard colors for different embed types (using Deadside palette)
    private static final Color SUCCESS_COLOR = DEADSIDE_SECONDARY;            // Success green
    private static final Color ERROR_COLOR = new Color(231, 76, 60);          // Error red
    private static final Color INFO_COLOR = DEADSIDE_PRIMARY;                 // Info emerald
    private static final Color WARNING_COLOR = new Color(243, 156, 18);       // Warning orange
    
    /**
     * Create a success embed
     */
    public static MessageEmbed successEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("✅ " + title)
                .setDescription(description)
                .setColor(SUCCESS_COLOR)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create an error embed
     */
    public static MessageEmbed errorEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("❌ " + title)
                .setDescription(description)
                .setColor(ERROR_COLOR)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create an info embed
     */
    public static MessageEmbed infoEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("ℹ️ " + title)
                .setDescription(description)
                .setColor(INFO_COLOR)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a warning embed
     */
    public static MessageEmbed warningEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("⚠️ " + title)
                .setDescription(description)
                .setColor(WARNING_COLOR)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a custom colored embed
     */
    public static MessageEmbed customEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a custom colored embed with thumbnail
     */
    public static MessageEmbed customEmbedWithThumbnail(String title, String description, 
                                                       Color color, String thumbnailUrl) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color)
                .setThumbnail(thumbnailUrl)
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a player stats embed
     */
    public static EmbedBuilder playerStatsEmbed(String playerName) {
        return new EmbedBuilder()
                .setTitle("📊 Stats for " + playerName)
                .setColor(DEADSIDE_ACCENT) // Accent color for stats
                .setFooter("Deadside Stats", ResourceManager.getWeaponStatsLogoUrl())
                .setTimestamp(Instant.now());
    }
    
    /**
     * Create a killfeed embed
     */
    public static MessageEmbed killfeedEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(new Color(204, 0, 0)) // Dark red for killfeed (kept for visibility)
                .setFooter("Deadside Killfeed", ResourceManager.getKillfeedLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a faction embed
     */
    public static MessageEmbed factionEmbed(String title, String description, Color color) {
        return new EmbedBuilder()
                .setTitle("🛡️ " + title)
                .setDescription(description)
                .setColor(color != null ? color : DEADSIDE_PRIMARY)
                .setFooter("Deadside Factions", ResourceManager.getFactionLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create an economy embed
     */
    public static MessageEmbed economyEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("💰 " + title)
                .setDescription(description)
                .setColor(DEADSIDE_SECONDARY)
                .setFooter("Deadside Economy", ResourceManager.getTraderLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a premium feature embed
     */
    public static MessageEmbed premiumEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("✨ " + title)
                .setDescription(description)
                .setColor(DEADSIDE_ACCENT)
                .setFooter("Deadside Premium", ResourceManager.getMainLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a server event embed
     */
    public static MessageEmbed eventEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("🔔 " + title)
                .setDescription(description)
                .setColor(DEADSIDE_DARK)
                .setFooter("Deadside Events", ResourceManager.getHelicrashLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a bounty embed
     */
    public static MessageEmbed bountyEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("🏆 " + title)
                .setDescription(description)
                .setColor(DEADSIDE_SECONDARY)
                .setFooter("Deadside Bounties", ResourceManager.getBountyLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create an airdrop embed
     */
    public static MessageEmbed airdropEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("📦 " + title)
                .setDescription(description)
                .setColor(DEADSIDE_LIGHT)
                .setFooter("Deadside Airdrops", ResourceManager.getAirdropLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a mission embed
     */
    public static MessageEmbed missionEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("🎯 " + title)
                .setDescription(description)
                .setColor(new Color(204, 51, 51)) // Red for missions
                .setFooter("Deadside Missions", ResourceManager.getMissionLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
    
    /**
     * Create a connections embed
     */
    public static MessageEmbed connectionsEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("🔗 " + title)
                .setDescription(description)
                .setColor(DEADSIDE_ACCENT)
                .setFooter("Deadside Connections", ResourceManager.getConnectionsLogoUrl())
                .setTimestamp(Instant.now())
                .build();
    }
}