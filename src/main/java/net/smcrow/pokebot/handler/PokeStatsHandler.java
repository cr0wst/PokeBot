package net.smcrow.pokebot.handler;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonStat;
import net.smcrow.pokebot.constants.PokemonList;
import org.jibble.pircbot.Colors;

/**
 * Handler for the Pokemon Stats !stats Command
 * Created by crow on 11/19/16.
 */
public class PokeStatsHandler {

    /**
     * Build the response to send back to the channel.  Eventually we should put this in a transfer object
     * and give the target back.  If the !stats command is not read correctly, send back the help text.
     * @param channel The channel.
     * @param sender The sender.
     * @param message The handler
     * @return The response as a String to send back to the channel.
     */
    public static String buildResponse(String channel, String sender, String message) {
        if (message != null) {
            // If only "!stats" is sent, display instructions.  Otherwise we know they sent at least !stats
            // to get to this handler.
            if ("!stats".equalsIgnoreCase(message)) {
                return buildStatsHelp(sender);
            } else if (message.contains(" ")) {
                return buildStats(sender, message);
            }
        }

        return "";
    }

    /**
     * The standard help text.  Should move to a constant.
     * @param sender The sender.
     * @return The help text.
     */
    private static String buildStatsHelp(String sender) {
        return sender + ": !stats <pokemon> to view base stats.";
    }

    /**
     * Method for building stats using the PokeAPI.  Does some basic validation, could be improved.
     * @param sender The sender.
     * @param message The handler that the sender sent.
     * @return The stats handler.
     */
    private static String buildStats(String sender, String message) {
        String response = "";
        String[] messageComponents = message.split(" ");

        if (messageComponents.length > 1) {
            String query = messageComponents[1];
            // Some pokemon have a space between their names
            if (messageComponents.length > 2) {
                query += " " + messageComponents[2];
            }

            int pokemonId = PokemonList.getPokemonIdByName(query);
            // Pokemon ID = 0 => Pokemon Not Found
            if (pokemonId > 0) {
                final PokeApi api = new PokeApiClient();
                Pokemon pokemonInfo = api.getPokemon(PokemonList.getPokemonIdByName(query));

                //TODO: Clean this up.
                response += sender + ": Stats for " + Colors.BOLD + pokemonInfo.getName().toUpperCase()
                        + Colors.BOLD + " - ";
                for (PokemonStat stat : pokemonInfo.getStats()) {
                    response += stat.getStat().getName() + ": " + stat.getBaseStat() + " ";
                }
            } else {
                return "Couldn't find stats for " + Colors.BOLD + query.toUpperCase() + Colors.BOLD;
            }
        }

        return response;
    }
}
