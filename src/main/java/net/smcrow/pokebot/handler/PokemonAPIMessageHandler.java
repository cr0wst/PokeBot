package net.smcrow.pokebot.handler;

/**
 * Created by crow on 11/19/16.
 * Handler for handling onMessage events that pass through the pokemon api.
 */
public class PokemonAPIMessageHandler {

    /**
     * List of commands that are supported by this handler.
     */
    private static final String [] HANDLED_MESSAGES = {"!stats"};

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
            //TODO: Consider making this into a private enumeration that can be switched over.
            if (message.startsWith("!stats")) {
                return PokeStatsHandler.buildResponse(channel, sender, message);
            }
        }

        return "";
    }
}
