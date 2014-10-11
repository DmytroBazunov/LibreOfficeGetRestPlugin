package com.github.binnarywolf.json;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Class contains method for parsing json structures.
 * @author <a href="mailto:binnarywolf@gmail.com">Dmitriy Bazunov</a>
 */
public final class JSONParser
{
    /**
     * Default private constructor designed to forbid creation an instance of
     * JSONParser class.
     * @author <a href="mailto:binnarywolf@gmail.com">Dmitriy Bazunov</a>
     */
    private JSONParser()
    {
    }

    /**
     * Parse JSON according to given pattern.
     * @param aJSON
     *        String contains the JSON structure to parse
     * @param aParsePattern
     *        String contains the pattern which is says how to parse JSON
     * @return Valid JSON Substructure if parameters are correct and error
     *         message otherwise.
     */
    public static String
            parseJSON(final String aJSON, final String aParsePattern)
    {
        System.out.println("Start json");
        String result = aJSON;
        try {
            final String[] commands = aParsePattern.trim().split("\\.");
            System.out.println(Arrays.toString(commands));
            System.out.println("before map");
            final ObjectMapper mapper = new ObjectMapper();
            System.out.println("after map");
            final JsonFactory factory = mapper.getJsonFactory();
            final JsonParser jp = factory.createJsonParser(aJSON);
            JsonNode actualObj = mapper.readTree(jp);
            for (String command : commands) {
                actualObj = executeCommand(actualObj, command);
            }
            if (actualObj != null) {
                if (actualObj.isTextual()) {
                    result = actualObj.asText();
                }
                else {
                    result = actualObj.toString();
                }
            }
            else {
                result = "No such node found.";
            }
        }
        catch (JsonParseException e) {
            result = "JSON parser exception " + e.getLocalizedMessage();
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Unknown exception: " + e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Execute parser command to move throw JSON parser tree.
     * @param aCurrentNode
     *        Current node of parsing tree.
     * @param aCommand
     *        String command which says what to do with current node.
     * @return Child node of the aCurrentNode according to command or null if
     *         there no such node
     */
    private static JsonNode
            executeCommand(final JsonNode aCurrentNode, final String aCommand)
    {
        JsonNode newNode = null;
        if (aCurrentNode != null) {
            System.out.println("Node before " + aCurrentNode);
            if (isGet(aCommand)) {
                final String argument = aCommand.substring(4,
                        aCommand.length() - 1);
                final int index = Integer.parseInt(argument);
                newNode = aCurrentNode.get(index);
            }
            else {
                newNode = aCurrentNode.get(aCommand);
            }
            System.out.println("Node after " + newNode);
        }
        return newNode;
    }

    /**
     * Check if command is a valid get command or not.
     * @param aCommand
     *        String that represent command to JSON parser.
     * @return Boolean which represen is command a getCommand(true) or
     *         objectNameCommand(false).
     */
    private static boolean isGet(final String aCommand)
    {
        final Pattern commandPattern = Pattern.compile("get\\(\\d+\\)");
        final Matcher regexpMatcher = commandPattern.matcher(aCommand);
        return regexpMatcher.matches();
    }
}
