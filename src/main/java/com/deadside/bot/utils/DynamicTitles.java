package com.deadside.bot.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Provides dynamic, lore-rich titles and contextual messages for all embeds
 * to prevent repetition and enhance immersion in the Deadside theme.
 */
public class DynamicTitles {
    private static final Random random = new Random();
    
    // Killfeed (PvP) titles
    private static final List<String> KILLFEED_TITLES = Arrays.asList(
        "Elimination Confirmed",
        "No Survivors",
        "Precision Eliminated",
        "Target Neutralized",
        "Combat Report",
        "Deadside Elimination",
        "Hostile Encounter",
        "Lethal Engagement",
        "Bloodshed in the Zone",
        "Deadside Claims Another"
    );
    
    // Bounty titles
    private static final List<String> BOUNTY_TITLES = Arrays.asList(
        "Contract Fulfilled",
        "Marked for Death",
        "Bounty Claimed",
        "Target Eliminated",
        "Blood Money Collected",
        "Hunter's Prize",
        "Contract Executed",
        "Bounty Hunter's Success"
    );
    
    // Suicide titles
    private static final List<String> SUICIDE_TITLES = Arrays.asList(
        "Self-Termination Logged",
        "Silent Exit",
        "Final Decision",
        "Personal Ending",
        "Self-Inflicted End",
        "Last Journey's End",
        "Final Choice Made"
    );
    
    // Falling death titles
    private static final List<String> FALLING_TITLES = Arrays.asList(
        "Terminal Velocity",
        "Gravity Claimed Another",
        "Fatal Descent",
        "The Ground Won",
        "Deadly Fall",
        "Height Miscalculation",
        "Sudden Stop at the Bottom"
    );
    
    // Leaderboard titles
    private static final List<String> LEADERBOARD_TITLES = Arrays.asList(
        "Apex Predators",
        "Top Hunters of the Week",
        "Factions at War",
        "Deadside's Deadliest",
        "Survival Rankings",
        "Kill Hierarchy",
        "Zone Dominance Chart",
        "Deadside's Elite"
    );
    
    // Event titles
    private static final List<String> EVENT_TITLES = Arrays.asList(
        "Zone Alert",
        "Emergency Broadcast",
        "Deadside Occurrence",
        "Situation Report",
        "Breaking Development",
        "Special Notice",
        "Urgent Transmission"
    );
    
    // Mission titles
    private static final List<String> MISSION_TITLES = Arrays.asList(
        "Mission Directive",
        "Operation Active",
        "High Priority Task",
        "Critical Assignment",
        "Strategic Objective",
        "Covert Operation",
        "Field Mission"
    );
    
    // Airdrop titles
    private static final List<String> AIRDROP_TITLES = Arrays.asList(
        "Supply Drop Inbound",
        "Airdrop Detected",
        "Resources Deployed",
        "Valuable Supplies",
        "Emergency Provisions",
        "Precious Cargo Dropped",
        "Strategic Supplies"
    );
    
    // Helicrash titles
    private static final List<String> HELICRASH_TITLES = Arrays.asList(
        "Helicopter Down",
        "Crash Site Located",
        "Wreckage Detected",
        "Pilot Error",
        "Downed Aircraft",
        "Fatal Aviation Incident",
        "Valuable Wreckage"
    );
    
    // Economy titles
    private static final List<String> ECONOMY_TITLES = Arrays.asList(
        "Financial Transaction",
        "Economic Update",
        "Resource Exchange",
        "Market Report",
        "Currency Movement",
        "Trade Results",
        "Asset Reallocation"
    );
    
    // Server status titles
    private static final List<String> SERVER_TITLES = Arrays.asList(
        "Server Status",
        "Zone Conditions",
        "Operational Report",
        "Infrastructure Update",
        "System Notification",
        "Technical Briefing",
        "Environment Status"
    );
    
    /**
     * Get a random title for killfeed embeds
     */
    public static String getKillfeedTitle() {
        return getRandomItem(KILLFEED_TITLES);
    }
    
    /**
     * Get a random title for bounty embeds
     */
    public static String getBountyTitle() {
        return getRandomItem(BOUNTY_TITLES);
    }
    
    /**
     * Get a random title for suicide embeds
     */
    public static String getSuicideTitle() {
        return getRandomItem(SUICIDE_TITLES);
    }
    
    /**
     * Get a random title for falling death embeds
     */
    public static String getFallingTitle() {
        return getRandomItem(FALLING_TITLES);
    }
    
    /**
     * Get a random title for leaderboard embeds
     */
    public static String getLeaderboardTitle() {
        return getRandomItem(LEADERBOARD_TITLES);
    }
    
    /**
     * Get a random title for event embeds
     */
    public static String getEventTitle() {
        return getRandomItem(EVENT_TITLES);
    }
    
    /**
     * Get a random title for mission embeds
     */
    public static String getMissionTitle() {
        return getRandomItem(MISSION_TITLES);
    }
    
    /**
     * Get a random title for airdrop embeds
     */
    public static String getAirdropTitle() {
        return getRandomItem(AIRDROP_TITLES);
    }
    
    /**
     * Get a random title for helicrash embeds
     */
    public static String getHelicrashTitle() {
        return getRandomItem(HELICRASH_TITLES);
    }
    
    /**
     * Get a random title for economy embeds
     */
    public static String getEconomyTitle() {
        return getRandomItem(ECONOMY_TITLES);
    }
    
    /**
     * Get a random title for server status embeds
     */
    public static String getServerTitle() {
        return getRandomItem(SERVER_TITLES);
    }
    
    /**
     * Generate a dynamic killfeed description
     */
    public static String getKillfeedDescription(String killer, String victim, String weapon, int distance) {
        List<String> descriptions = Arrays.asList(
            killer + " removed " + victim + " at " + distance + "m with " + weapon,
            killer + " eliminated " + victim + " from " + distance + "m using " + weapon,
            killer + " neutralized " + victim + " with " + weapon + " (" + distance + "m)",
            "Clean shot by " + killer + ". " + victim + " never saw it coming.",
            victim + " fell to " + killer + "'s " + weapon + " at " + distance + " meters",
            "Through smoke and fog, " + killer + " found " + victim + " with deadly accuracy",
            killer + " proved more lethal than " + victim + " in this encounter",
            "The wilderness claims another as " + killer + " overpowers " + victim
        );
        return getRandomItem(descriptions);
    }
    
    /**
     * Generate a dynamic leaderboard description
     */
    public static String getLeaderboardDescription() {
        List<String> descriptions = Arrays.asList(
            "Survivors ranked by raw efficiency:",
            "The strongest endure. Here's who leads:",
            "This week's dominant forces in the zone:",
            "Blood and skill determine rank here:",
            "The most dangerous hunters roaming Deadside:",
            "Those who've claimed the most lives:",
            "Ranking the most feared names in the wasteland:",
            "Kill or be killed - these chose the former:"
        );
        return getRandomItem(descriptions);
    }
    
    /**
     * Generate a dynamic suicide description
     */
    public static String getSuicideDescription(String player) {
        List<String> descriptions = Arrays.asList(
            player + " chose a different path",
            "Sometimes the biggest threat is yourself. " + player + " found that out.",
            player + " ended their own journey",
            "The wasteland claims another soul as " + player + " succumbs",
            player + " found peace in oblivion",
            "The struggle was too much for " + player,
            "A quiet end for " + player + " by their own hand"
        );
        return getRandomItem(descriptions);
    }
    
    /**
     * Generate a dynamic falling description
     */
    public static String getFallingDescription(String player, int height) {
        List<String> descriptions = Arrays.asList(
            player + " fell from " + height + "m and didn't survive",
            "Gravity is undefeated. " + player + " learned at " + height + "m",
            player + " misjudged the drop by " + height + "m",
            "A " + height + "m fall was too much for " + player,
            player + " discovered that " + height + "m is beyond human endurance",
            "The ground rushed up to meet " + player + " after a " + height + "m drop",
            "From " + height + "m up, " + player + " made their final descent"
        );
        return getRandomItem(descriptions);
    }
    
    /**
     * Get a random item from a list
     */
    private static <T> T getRandomItem(List<T> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        return items.get(random.nextInt(items.size()));
    }
}