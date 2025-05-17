package com.deadside.bot.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for sending embeds with file attachments for thumbnails
 */
public class EmbedSender {
    private static final Pattern ATTACHMENT_PATTERN = Pattern.compile("attachment://([\\w\\d.]+)");
    
    /**
     * Extract attachment filenames from an embed
     * 
     * @param embed The embed to extract from
     * @return List of filenames referenced in the embed
     */
    public static List<String> extractAttachmentFilenames(MessageEmbed embed) {
        List<String> filenames = new ArrayList<>();
        
        // Check thumbnail
        if (embed.getThumbnail() != null && embed.getThumbnail().getUrl() != null) {
            extractFilename(embed.getThumbnail().getUrl(), filenames);
        }
        
        // Check image
        if (embed.getImage() != null && embed.getImage().getUrl() != null) {
            extractFilename(embed.getImage().getUrl(), filenames);
        }
        
        // Check author icon
        if (embed.getAuthor() != null && embed.getAuthor().getIconUrl() != null) {
            extractFilename(embed.getAuthor().getIconUrl(), filenames);
        }
        
        // Check footer icon
        if (embed.getFooter() != null && embed.getFooter().getIconUrl() != null) {
            extractFilename(embed.getFooter().getIconUrl(), filenames);
        }
        
        return filenames;
    }
    
    private static void extractFilename(String url, List<String> filenames) {
        Matcher matcher = ATTACHMENT_PATTERN.matcher(url);
        if (matcher.find()) {
            filenames.add(matcher.group(1));
        }
    }
    
    /**
     * Send an embed to a channel with needed file attachments
     * 
     * @param channel The channel to send to
     * @param embed The embed to send
     * @return CompletableFuture for the sent message
     */
    public static CompletableFuture<?> sendEmbed(MessageChannel channel, MessageEmbed embed) {
        List<String> filenames = extractAttachmentFilenames(embed);
        
        if (filenames.isEmpty()) {
            return channel.sendMessageEmbeds(embed).submit();
        } else {
            FileUpload[] uploads = new FileUpload[filenames.size()];
            for (int i = 0; i < filenames.size(); i++) {
                uploads[i] = ResourceManager.getImageAsFileUpload(filenames.get(i));
                if (uploads[i] == null) {
                    // If we can't get a file upload, try to send without it
                    return channel.sendMessageEmbeds(embed).submit();
                }
            }
            
            return channel.sendMessageEmbeds(embed).addFiles(uploads).submit();
        }
    }
    
    /**
     * Send an embed as a reply to an interaction with needed file attachments
     * 
     * @param interaction The interaction to reply to
     * @param embed The embed to send
     * @param ephemeral Whether the reply should be ephemeral
     */
    public static void replyEmbed(IReplyCallback interaction, MessageEmbed embed, boolean ephemeral) {
        List<String> filenames = extractAttachmentFilenames(embed);
        
        if (filenames.isEmpty()) {
            interaction.replyEmbeds(embed).setEphemeral(ephemeral).queue();
        } else {
            FileUpload[] uploads = new FileUpload[filenames.size()];
            for (int i = 0; i < filenames.size(); i++) {
                uploads[i] = ResourceManager.getImageAsFileUpload(filenames.get(i));
                if (uploads[i] == null) {
                    // If we can't get a file upload, try to send without it
                    interaction.replyEmbeds(embed).setEphemeral(ephemeral).queue();
                    return;
                }
            }
            
            interaction.replyEmbeds(embed).addFiles(uploads).setEphemeral(ephemeral).queue();
        }
    }
    
    /**
     * Send an embed through an interaction hook with needed file attachments
     * 
     * @param hook The interaction hook to send through
     * @param embed The embed to send
     */
    public static void sendEmbed(InteractionHook hook, MessageEmbed embed) {
        List<String> filenames = extractAttachmentFilenames(embed);
        
        if (filenames.isEmpty()) {
            hook.sendMessageEmbeds(embed).queue();
        } else {
            FileUpload[] uploads = new FileUpload[filenames.size()];
            for (int i = 0; i < filenames.size(); i++) {
                uploads[i] = ResourceManager.getImageAsFileUpload(filenames.get(i));
                if (uploads[i] == null) {
                    // If we can't get a file upload, try to send without it
                    hook.sendMessageEmbeds(embed).queue();
                    return;
                }
            }
            
            hook.sendMessageEmbeds(embed).addFiles(uploads).queue();
        }
    }
}