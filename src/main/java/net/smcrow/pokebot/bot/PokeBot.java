package net.smcrow.pokebot.bot;

import net.smcrow.pokebot.constants.Settings;
import net.smcrow.pokebot.message.PokemonAPIMessageHandler;
import org.jibble.pircbot.PircBot;

/**
 * Pokemon Bot for IRC
 * Created by crow on 11/19/16.
 */
public class PokeBot extends PircBot {
    public PokeBot() {
        this.setName(Settings.BOT_NICK);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        // Determine if this is an api message or not
        if (PokemonAPIMessageHandler.canHandle(message)) {
            this.sendMessage(channel, PokemonAPIMessageHandler.buildResponse(channel, sender, message));
        } else {
            //this.sendAction(channel, "cannot handle with PokemonAPIMessageHandler");
        }
    }
}
