package net.smcrow.pokebot.handler;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by crow on 11/19/16.
 * Handler for handling onMessage events that pass through the pokemon api.
 */
public class PokemonAPIMessageHandler {

    /**
     * List of commands that are supported by this handler.
     */
    //TODO: Switch to static factory which would allow for the pre-loading of handlers and let them list their commands.
    private static final String [] HANDLED_MESSAGES = {"!stats", "!evolve"};

    /**
     * Determines if the handler should handle the handler based on the prefix.
     * @param message The handler coming in.
     * @return True or False depending on if the handler should handle it.
     */
    public static boolean canHandle(String message) {
        for (String command : HANDLED_MESSAGES) {
            if (message != null && message.startsWith(command)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Build the response using other handlers.
     * @param channel The channel.
     * @param sender The sender.
     * @param message The handler that was sent.
     * @return A string to send back to the channel.
     */
    public static String buildResponse(String channel, String sender, String message) {
        if (message != null) {
            String command;
            String params = "";
            if (StringUtils.contains(message, ' ')) {
                final int firstSpaceIndex = StringUtils.indexOf(message, ' ');
                command = StringUtils.substring(message, 0, firstSpaceIndex);
                params = StringUtils.substring(message, firstSpaceIndex + 1);
            } else {
                command = message;
            }

            MessageResponseHandler handler;
            switch (command) {
                case "!stats":
                    handler = new PokeStatsHandler();
                    break;
                case "!evolve":
                    handler = new PokeEvolveHandler();
                    break;
                default:
                    // This should never happen, but just in case it does, we will handle it.  Insures
                    // that handler always gets initialized.
                    return "";
            }

            return handler.buildResponse(channel, sender, command, params);
        }

        return "";
    }
}
