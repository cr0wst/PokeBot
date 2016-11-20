package net.smcrow.pokebot;

import net.smcrow.pokebot.bot.PokeBot;
import net.smcrow.pokebot.constants.Settings;
import org.jibble.pircbot.IrcException;

import java.io.IOException;

/**
 * Created by crow on 11/19/16.
 * Main class for Application.
 */
public class Main {
    public static void main(String [] args) {
        PokeBot bot = new PokeBot();

        bot.setVerbose(true);

        try {
            bot.connect(Settings.DEFAULT_SERVER);
            bot.joinChannel(Settings.DEFAULT_CHANNEL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }
}
