package net.smcrow.pokebot.bot;

import net.smcrow.pokebot.constants.Settings;
import net.smcrow.pokebot.handler.PokemonAPIMessageHandler;
import org.jibble.pircbot.PircBot;

import java.util.ArrayList;
import java.util.List;

/**
 * Pokemon Bot for IRC
 * Created by crow on 11/19/16.
 */
public class PokeBot extends PircBot {
    private static int IRC_LINE_MAX = 400;
    public PokeBot() {
        this.setName(Settings.BOT_NICK);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        // Determine if this is an api handler or not
        if (PokemonAPIMessageHandler.canHandle(message)) {
            // Have to send the message on multiple lines if it's too big.
            String response = PokemonAPIMessageHandler.buildResponse(channel, sender, message);
            if (response.length() > IRC_LINE_MAX) {
                List<String> outputLines = new ArrayList<>();
                int index = 0;
                while (index < response.length()) {
                    outputLines.add(response.substring(index, Math.min(index + IRC_LINE_MAX, response.length())));
                    index += IRC_LINE_MAX;
                }

                for (String line : outputLines) {
                    this.sendMessage(channel, line);
                }
            } else {
                this.sendMessage(channel, response);
            }
        }
    }
}
