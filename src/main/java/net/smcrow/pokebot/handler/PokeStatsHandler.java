package net.smcrow.pokebot.handler;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import me.sargunvohra.lib.pokekotlin.model.PokemonStat;
import net.smcrow.pokebot.constants.PokemonList;
import org.apache.commons.lang3.StringUtils;
import org.jibble.pircbot.Colors;

/**
 * Handler for the Pokemon Stats !stats Command
 * Created by crow on 11/19/16.
 */
class PokeStatsHandler extends MessageResponseHandler {
    /**
     * Private enumeration to handle translating the stat name from the API to the IRC-Output name.
     */
    private enum StatName {
        SPD ("speed", "SPD"),
        SP_D ("special-defense", "SpDEF"),
        SP_A ("special-attack", "SpATK"),
        DEF ("defense", "DEF"),
        ATK ("attack", "ATK"),
        HP ("hp", "HP"),
        UNKNOWN_STAT ("", "");
        String apiName;
        String outputName;

        StatName(String apiName, String outputName) {
            this.apiName = apiName;
            this.outputName = outputName;
        }

        public static StatName lookupStatFromApiName(String apiName) {
            if (StringUtils.isNotBlank(apiName)) {
                for (StatName stat : StatName.values()) {
                    if (stat.apiName.equals(apiName)) {
                        return stat;
                    }
                }
            }
            return UNKNOWN_STAT;
        }

        /**
         * @return The output name for the stat.
         */
        public String getOutputName() {
            return this.outputName;
        }

    }

    @Override
    protected String helpMessage(String sender) {
        return sender + ": !stats <pokemon> to view base stats.";
    }

    @Override
    protected String responseMessage(String pokemonName) {
        String response = "";
        int pokemonId = PokemonList.getPokemonIdByName(pokemonName);
            // Pokemon ID = 0 => Pokemon Not Found
            if (pokemonId > 0) {
                final PokeApi api = new PokeApiClient();
                Pokemon pokemonInfo = api.getPokemon(pokemonId);

                // Header Line
                response += "Stats for " + Colors.RED + pokemonInfo.getName().toUpperCase() + Colors.NORMAL + " - ";

                // Build the Statistics from the API
                for (PokemonStat stat : pokemonInfo.getStats()) {
                    response += Colors.BOLD
                    + StatName.lookupStatFromApiName(stat.getStat().getName()).getOutputName() + ": "
                    + Colors.BOLD
                    + stat.getBaseStat() + " ";
                }
            } else {
                response = "Couldn't find stats for " + Colors.BOLD + pokemonName.toUpperCase() + Colors.BOLD;
            }
        return response;
    }
}
