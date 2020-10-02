package com.iggyvolz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EHBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException 
    {
        String token;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("discord.config"));
            token = props.getProperty("discord.token");
        } catch(IOException e) {
            System.out.println("could not find discord token: " + e.getMessage());
            return;
        }
        JDABuilder.createDefault(token)
            .addEventListeners(new EHBot())
            .build();
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if(event.getChannel().getIdLong() == 761327969083195403L) {
            List<Role> roles = event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete().getMentionedRoles();
            if(roles.size()==1) {
                Role role = roles.get(0);
                PrivateChannel pm = event.getUser().openPrivateChannel().complete();
                pm.sendMessage("Adding you to role " + role.getName()).complete();
                event.getGuild().addRoleToMember(event.getMember(), role).queue();
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if(event.getChannel().getIdLong() == 761327969083195403L) {
            List<Role> roles = event.getChannel().retrieveMessageById(event.getMessageIdLong()).complete().getMentionedRoles();
            if(roles.size()==1) {
                Role role = roles.get(0);
                PrivateChannel pm = event.getUser().openPrivateChannel().complete();
                pm.sendMessage("Removing you from role " + role.getName()).complete();
                event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
            }
        }
    }
}