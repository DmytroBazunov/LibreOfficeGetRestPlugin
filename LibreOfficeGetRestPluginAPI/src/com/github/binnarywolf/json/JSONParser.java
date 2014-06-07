package com.github.binnarywolf.json;

import java.io.IOException;
import java.util.Arrays;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONParser {

	public static String parseJSON(String json, String parsePattern) {
		String result = json;
		try {
			String[] commands = parsePattern.trim().split("\\.");
			System.out.println(Arrays.toString(commands));
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser jp;
			jp = factory.createJsonParser(json);

			JsonNode actualObj = mapper.readTree(jp);
			for (String command : commands) {
				actualObj = executeCommand(actualObj, command);
			}
			if (actualObj != null) {
				if (actualObj.isTextual()) {
					result = actualObj.asText();
				} else {
					result = actualObj.toString();
				}
			} else {
				result = "No such node found.";
			}
		} catch (JsonParseException e) {
			result = "JSON parser exception";
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static JsonNode executeCommand(JsonNode currentNode, String command) {
		JsonNode newNode = null;
		if (currentNode != null) {
			System.out.println("Node before " + currentNode);
			String argument = command.substring(4, command.length() - 1);
			if (command.startsWith("get(")) {
				int index = Integer.parseInt(argument);
				newNode = currentNode.get(index);
			} else {
				newNode = currentNode.get(command);
			}
			System.out.println("Node after " + newNode);
		}
		return newNode;
	}
}
