package net.smcrow.pokebot.handler;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by steve on 11/20/2016.
 */
abstract class MessageResponseHandler {

    String buildResponse(String channel, String sender, String command, String params) {
        // On blank params, send the help message:
        if (StringUtils.isBlank(params)) {
            return helpMessage(sender);
        }

        return responseMessage(params);
    }

    /**
     * The help message if an invalid request was received.
     * @param sender The sender of the request.
     * @return The help message explaining the command.
     */
    protected abstract String helpMessage(String sender);

    /**
     * Build the message to send back in response after utilizing the API.
     * @param params The parameters as a String.
     * @return The message to send back to the sender.
     */
    protected abstract String responseMessage(String params);
}
