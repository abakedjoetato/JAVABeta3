package com.deadside.bot.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages resources for the Deadside Bot including logos and images
 */
public class ResourceManager {
    // Paths for the resource folders
    private static final String MAIN_RESOURCES_PATH = "src/main/resources";
    private static final String CLASS_RESOURCES_PATH = "com/deadside/bot/resources";
    
    // Image resources for embeds
    private static final String IMAGES_FOLDER = "/images/";
    
    // Image filenames
    public static final String MAIN_LOGO = "Mainlogo.png";
    public static final String KILLFEED_ICON = "Killfeed.png";
    public static final String BOUNTY_ICON = "Bounty.png";
    public static final String MISSION_ICON = "Mission.png";
    public static final String FACTION_ICON = "Faction.png";
    public static final String AIRDROP_ICON = "Airdrop.png";
    public static final String TRADER_ICON = "Trader.png";
    public static final String CONNECTIONS_ICON = "Connections.png";
    public static final String WEAPON_STATS_ICON = "WeaponStats.png";
    public static final String HELICRASH_ICON = "Helicrash.png";
    
    // Cache for FileUpload objects
    private static final Map<String, FileUpload> fileUploadCache = new HashMap<>();
    
    /**
     * Get a FileUpload for an image resource
     * @param imageName The name of the image file
     * @return FileUpload object for the image
     */
    public static FileUpload getImageAsFileUpload(String imageName) {
        // Check cache first
        if (fileUploadCache.containsKey(imageName)) {
            FileUpload cached = fileUploadCache.get(imageName);
            if (cached != null) {
                // We can't reuse FileUpload objects, so create a new one
                // from the same location as the cached one
                try {
                    // Try to look up the original file again
                    String classPath = CLASS_RESOURCES_PATH + IMAGES_FOLDER + imageName;
                    File file = new File(classPath);
                    
                    if (file.exists()) {
                        return FileUpload.fromData(file, imageName);
                    }
                    
                    // Try main resources folder next
                    String mainPath = MAIN_RESOURCES_PATH + IMAGES_FOLDER + imageName;
                    file = new File(mainPath);
                    
                    if (file.exists()) {
                        return FileUpload.fromData(file, imageName);
                    }
                    
                    // Try attached_assets folder as fallback
                    String attachedPath = "attached_assets/" + imageName;
                    file = new File(attachedPath);
                    
                    if (file.exists()) {
                        return FileUpload.fromData(file, imageName);
                    }
                    
                    // If all else fails, return null and let the caller handle it
                    return null;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        
        // Try to load from different possible locations
        try {
            // Try class resources first
            String classPath = CLASS_RESOURCES_PATH + IMAGES_FOLDER + imageName;
            File file = new File(classPath);
            
            if (file.exists()) {
                FileUpload fileUpload = FileUpload.fromData(file, imageName);
                fileUploadCache.put(imageName, fileUpload);
                return fileUpload;
            }
            
            // Try main resources folder next
            String mainPath = MAIN_RESOURCES_PATH + IMAGES_FOLDER + imageName;
            file = new File(mainPath);
            
            if (file.exists()) {
                FileUpload fileUpload = FileUpload.fromData(file, imageName);
                fileUploadCache.put(imageName, fileUpload);
                return fileUpload;
            }
            
            // Try attached_assets folder as fallback
            String attachedPath = "attached_assets/" + imageName;
            file = new File(attachedPath);
            
            if (file.exists()) {
                FileUpload fileUpload = FileUpload.fromData(file, imageName);
                fileUploadCache.put(imageName, fileUpload);
                return fileUpload;
            }
            
            // Try classpath resource loading as last resort
            try (InputStream inputStream = ResourceManager.class.getClassLoader().getResourceAsStream("images/" + imageName)) {
                if (inputStream != null) {
                    Path tempFile = Files.createTempFile("resource-", imageName);
                    Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    FileUpload fileUpload = FileUpload.fromData(tempFile.toFile(), imageName);
                    fileUploadCache.put(imageName, fileUpload);
                    return fileUpload;
                }
            }
            
            // If we get here, the resource was not found
            System.err.println("Resource not found: " + imageName);
            return null;
            
        } catch (Exception e) {
            System.err.println("Error loading resource: " + imageName);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get an array of FileUploads for the given image names
     * @param imageNames The names of the image files
     * @return Array of FileUpload objects
     */
    public static FileUpload[] getImagesAsFileUploads(String... imageNames) {
        FileUpload[] uploads = new FileUpload[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            uploads[i] = getImageAsFileUpload(imageNames[i]);
        }
        return uploads;
    }
    
    /**
     * Get the attachment syntax for a specific image
     * @param imageName The name of the image file
     * @return The attachment syntax string for use in embeds
     */
    public static String getAttachmentString(String imageName) {
        return "attachment://" + imageName;
    }
}