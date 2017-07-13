package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.music.GuildMusicPlayer;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by ethan on 6/19/17.
 */
public class PauseCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[] {"pause"};
    }

    @Override
    public String getDescription() {
        return "Pauses playback until resumed with the play command. Does nothing if the player is already paused.\n" +
                "Usage: " + Bot.config.PREFIX + "pause";
    }

    @Override
    public String getShortDesc() {
        return "Pauses the player.";
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        GuildMusicPlayer musicPlayer;
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        musicPlayer = GuildMusicPlayer.getPlayer(guild);

        if (musicPlayer.isPaused()) {
            channel.sendMessage("The player is already paused!").queue();
        } else {
            if (musicPlayer.getPlayingTrack() == null) {
                channel.sendMessage("There's nothing in the queue to pause!").queue();
            } else {
                musicPlayer.setPaused(true);
                channel.sendMessage("Player paused!").queue();
            }
        }
    }
}
