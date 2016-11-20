package net.smcrow.pokebot.handler;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.ChainLink;
import me.sargunvohra.lib.pokekotlin.model.EvolutionDetail;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;
import net.smcrow.pokebot.constants.PokemonList;
import org.apache.commons.lang3.StringUtils;
import org.jibble.pircbot.Colors;

/**
 * Created by steve on 11/20/2016.
 */
class PokeEvolveHandler extends MessageResponseHandler {
    @Override
    protected String helpMessage(String sender) {
        return sender + ": !evolve <pokemon> to view the evolution chain.";
    }

    @Override
    protected String responseMessage(String pokemonName) {
        String response = "";
        int pokemonId = PokemonList.getPokemonIdByName(pokemonName);

        if (pokemonId > 0) {
            final PokeApi api = new PokeApiClient();

            PokemonSpecies species = api.getPokemonSpecies(pokemonId);
            int evolutionChainId = species.getEvolutionChain().getId();
            // Header Line
            // Loop through the evolution chains to build the list of evolutions:

            ChainLink link = api.getEvolutionChain(evolutionChainId).getChain();
            response += "Evolution Chain (" + evolutionChainId + ") for " + Colors.RED + link.getSpecies().getName().toUpperCase() + Colors.NORMAL + " - ";
            int i;
            while (link.getEvolvesTo().size() > 0) {
                for (ChainLink evolutionLink : link.getEvolvesTo()) {
                    // Find the one relating to level
                    response += Colors.RED + evolutionLink.getSpecies().getName().toUpperCase() + Colors.NORMAL + " ";
                    for (EvolutionDetail detail : evolutionLink.getEvolutionDetails()) {
                        // Depending on the trigger, the message is different:
                        switch (detail.getTrigger().getName()) {
                            case "level-up":
                                response += "(" + Colors.BOLD + "LEVEL UP" + Colors.BOLD + " @ ";
                                if (detail.getMinLevel() != null) {
                                    response += Colors.BOLD + "LVL: " + Colors.BOLD + detail.getMinLevel() + " ";
                                }
                                if (detail.getMinHappiness() != null) {
                                    response += Colors.BOLD + "HAP: " + Colors.BOLD + detail.getMinHappiness() + " ";
                                }
                                if (detail.getMinBeauty() != null) {
                                    response += Colors.BOLD + "BEU: " + Colors.BOLD + detail.getMinBeauty() + " ";
                                }
                                if (detail.getMinAffection() != null) {
                                    response += Colors.BOLD + "AFF: " + Colors.BOLD + detail.getMinAffection() + " ";
                                }
                                if (StringUtils.isNotBlank(detail.getTimeOfDay())) {
                                    response += Colors.BOLD + "TIME: " + Colors.BOLD + detail.getTimeOfDay() + " ";
                                }
                                if (detail.getPartySpecies() != null) {
                                    response += Colors.BOLD + "IN PARTY: " + Colors.BOLD + detail.getPartySpecies().getName().toUpperCase() + " ";
                                }
                                if (detail.getPartyType() != null) {
                                    response += Colors.BOLD + "IN PARTY: " + Colors.BOLD + detail.getPartyType().getName() + Colors.BOLD + " TYPE " + Colors.BOLD;
                                }
                                if (detail.getLocation() != null) {
                                    response += Colors.BOLD + "LOC: " + Colors.BOLD + detail.getLocation().getName() + " ";
                                }
                                if (detail.getKnownMove() != null) {
                                    response += Colors.BOLD + "KN. MOVE: " + Colors.BOLD + detail.getKnownMove().getName() + " ";
                                }
                                if (detail.getNeedsOverworldRain()) {
                                    response += Colors.BOLD + "RAINING " + Colors.BOLD;
                                }
                                if (detail.getTurnUpsideDown()) {
                                    response += Colors.BOLD + "DS TURNT UP" + Colors.BOLD;
                                }
                                if (detail.getRelativePhysicalStats() != null) {
                                    response += Colors.BOLD + "REL. TO PHY. STATS " + Colors.BOLD;
                                }
                                response = response.trim() + ") ";
                                break;
                            case "trade":
                                response += "(" + Colors.BOLD + "TRADE W/ " + Colors.BOLD;
                                if (detail.getTradeSpecies() != null) {
                                    response += Colors.BOLD + "TRD FOR: " + Colors.BOLD + detail.getTradeSpecies().getName().toUpperCase();
                                }

                                if (detail.getHeldItem() != null) {
                                    response += Colors.BOLD + "HOLDING: " + Colors.BOLD + detail.getHeldItem().getName();
                                }
                                response = response.trim() + ") ";
                                break;
                            case "use-item":
                                if (detail.getItem() != null) {
                                    response += "(" + Colors.BOLD + "USE: " + Colors.BOLD + detail.getItem().getName() + ") ";
                                }
                                break;
                            case "shed":
                                response += "(" + Colors.BOLD + "SHED" + Colors.BOLD + ")";
                                break;
                            default:
                                response += "(Unknown) ";
                                break;
                        }
                        link = evolutionLink;
                    }
                }
            }
        } else {
            response = "Couldn't find evolutions for " + Colors.BOLD + pokemonName.toUpperCase() + Colors.BOLD;
        }
        return response;
    }
}
