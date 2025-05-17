package com.deadside.bot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating themed embeds for the Deadside bot
 */
public class EmbedThemes {
    // Deadside themed color palette - emerald colors
    public static final Color PRIMARY_COLOR = new Color(0, 168, 107);      // Primary emerald green
    public static final Color SECONDARY_COLOR = new Color(14, 105, 72);    // Darker emerald
    public static final Color ACCENT_COLOR = new Color(108, 235, 184);     // Light emerald accent
    
    // Event colors
    public static final Color EVENT_COLOR = new Color(230, 126, 34);       // Orange for events
    public static final Color KILL_COLOR = new Color(231, 76, 60);         // Red for kills
    public static final Color DEATH_COLOR = new Color(149, 165, 166);      // Gray for deaths
    public static final Color JOIN_COLOR = new Color(46, 204, 113);        // Green for joins
    public static final Color LEAVE_COLOR = new Color(155, 89, 182);       // Purple for leaves
    
    // Status colors
    public static final Color SUCCESS_COLOR = new Color(39, 174, 96);      // Green for success
    public static final Color WARNING_COLOR = new Color(241, 196, 15);     // Yellow for warnings
    public static final Color ERROR_COLOR = new Color(192, 57, 43);        // Red for errors
    public static final Color INFO_COLOR = new Color(52, 152, 219);        // Blue for info
    
    // Deadside logo and icon URLs - Using ResourceManager for transparent PNG attachments
    private static final String DEADSIDE_LOGO = ResourceManager.getAttachmentString(ResourceManager.MAIN_LOGO);
    private static final String SUCCESS_ICON = ResourceManager.getAttachmentString(ResourceManager.MISSION_ICON);
    private static final String WARNING_ICON = ResourceManager.getAttachmentString(ResourceManager.BOUNTY_ICON);
    private static final String ERROR_ICON = ResourceManager.getAttachmentString(ResourceManager.HELICRASH_ICON);
    private static final String INFO_ICON = ResourceManager.getAttachmentString(ResourceManager.CONNECTIONS_ICON);
    
    /**
     * Create a base themed embed with Deadside style
     */
    public static EmbedBuilder baseEmbed() {
        return new EmbedBuilder()
                .setColor(PRIMARY_COLOR)
                .setTimestamp(Instant.now())
                .setFooter("Powered By Discord.gg/EmeraldServers");
    }
    
    /**
     * Create an event embed (mission, airdrop, etc.)
     */
    public static MessageEmbed eventEmbed(String title, String description) {
        return baseEmbed()
                .setColor(EVENT_COLOR)
                .setTitle(title)
                .setDescription(description)
                .setThumbnail(SUCCESS_ICON)
                .build();
    }
    
    /**
     * Create a kill event embed
     */
    public static MessageEmbed killEmbed(String killer, String victim, String weapon, int distance) {
        return baseEmbed()
                .setColor(KILL_COLOR)
                .setTitle("Kill Feed")
                .setDescription(String.format("**%s** killed **%s**\nWeapon: **%s**\nDistance: **%dm**", 
                        killer, victim, weapon, distance))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.KILLFEED_ICON))
                .build();
    }
    
    /**
     * Create a death event embed (suicide or environment death)
     */
    public static MessageEmbed deathEmbed(String victim, String cause) {
        return baseEmbed()
                .setColor(DEATH_COLOR)
                .setTitle("Death Feed")
                .setDescription(String.format("**%s** died from **%s**", victim, cause))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.HELICRASH_ICON))
                .build();
    }
    
    /**
     * Create a player join embed
     */
    public static MessageEmbed joinEmbed(String playerName) {
        return baseEmbed()
                .setColor(JOIN_COLOR)
                .setTitle("Player Joined")
                .setDescription(String.format("**%s** joined the server", playerName))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.CONNECTIONS_ICON))
                .build();
    }
    
    /**
     * Create a player leave embed
     */
    public static MessageEmbed leaveEmbed(String playerName) {
        return baseEmbed()
                .setColor(LEAVE_COLOR)
                .setTitle("Player Left")
                .setDescription(String.format("**%s** left the server", playerName))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.CONNECTIONS_ICON))
                .build();
    }
    
    /**
     * Create a success embed
     */
    public static MessageEmbed successEmbed(String title, String description) {
        return baseEmbed()
                .setColor(SUCCESS_COLOR)
                .setTitle(title)
                .setDescription(description)
                .setThumbnail(SUCCESS_ICON)
                .build();
    }
    
    /**
     * Create a warning embed
     */
    public static MessageEmbed warningEmbed(String title, String description) {
        return baseEmbed()
                .setColor(WARNING_COLOR)
                .setTitle(title)
                .setDescription(description)
                .setThumbnail(WARNING_ICON)
                .build();
    }
    
    /**
     * Create an error embed
     */
    public static MessageEmbed errorEmbed(String title, String description) {
        return baseEmbed()
                .setColor(ERROR_COLOR)
                .setTitle(title)
                .setDescription(description)
                .setThumbnail(ERROR_ICON)
                .build();
    }
    
    /**
     * Create an info embed
     */
    public static MessageEmbed infoEmbed(String title, String description) {
        return baseEmbed()
                .setColor(INFO_COLOR)
                .setTitle(title)
                .setDescription(description)
                .setThumbnail(INFO_ICON)
                .build();
    }
    
    /**
     * Create a faction embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder factionEmbed() {
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(SECONDARY_COLOR))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.FACTION_ICON));
    }
    
    /**
     * Create a stats embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder statsEmbed() {
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(ACCENT_COLOR))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.WEAPON_STATS_ICON));
    }
    
    /**
     * Create an economy embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder economyEmbed() {
        Color goldColor = new Color(241, 196, 15); // Gold color for economy
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(goldColor))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.TRADER_ICON));
    }
    
    /**
     * Create a premium embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder premiumEmbed() {
        Color purpleColor = new Color(156, 89, 182); // Purple color for premium
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(purpleColor))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.MAIN_LOGO));
    }
    
    /**
     * Create a server embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder serverEmbed() {
        Color blueColor = new Color(52, 152, 219); // Blue color for server info
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(blueColor))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.CONNECTIONS_ICON));
    }
    
    /**
     * Create an airdrop embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder airdropEmbed() {
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(EVENT_COLOR))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.AIRDROP_ICON))
                .setTitle(DynamicTitles.getAirdropTitle());
    }
    
    /**
     * Create a mission embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder missionEmbed() {
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(EVENT_COLOR))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.MISSION_ICON))
                .setTitle(DynamicTitles.getMissionTitle());
    }
    
    /**
     * Create a helicrash embed with Phase 3 structure enhancements
     */
    public static EmbedBuilder helicrashEmbed() {
        return baseEmbed()
                .setColor(AccessibilityUtils.getAccessibleColor(ERROR_COLOR))
                .setThumbnail(ResourceManager.getAttachmentString(ResourceManager.HELICRASH_ICON))
                .setTitle(DynamicTitles.getHelicrashTitle());
    }
}