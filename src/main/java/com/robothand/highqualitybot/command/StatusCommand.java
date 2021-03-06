package com.robothand.highqualitybot.command;

import com.robothand.highqualitybot.Bot;
import com.robothand.highqualitybot.Config;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * StatusCommand.java
 * Displays information on the running instance of High Quality Bot.
 */
public class StatusCommand extends Command {
    @Override
    public String[] getNames() {
        return new String[]{"status", "version"};
    }

    @Override
    public String getDescription() {
        return "Prints the current running version of High Quality Bot.\n" +
                "Usage: " + Config.PREFIX + "status";
    }

    @Override
    public String getShortDesc() {
        return "Prints some helpful information about the bot.";
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("High Quality Bot version **").append(Bot.VERSION).append("**\n");
        messageBuilder.append("Check the GitHub repo at **https://github.com/sasquench/highqualitybot** for updates.");

        channel.sendMessage(messageBuilder.toString()).queue();
    }
}
