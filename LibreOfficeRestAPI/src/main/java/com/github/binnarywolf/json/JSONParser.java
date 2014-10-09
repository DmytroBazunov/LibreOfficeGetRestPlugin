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

public class JSONParser {

	public static String parseJSON(String json, String parsePattern) {
		System.out.println("Start json");
		String result = json;
		try {
			String[] commands = parsePattern.trim().split("\\.");
			System.out.println(Arrays.toString(commands));

			System.out.println("before map");
			ObjectMapper mapper = new ObjectMapper();

			System.out.println("after map");
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
		} catch (Exception e) {
			System.out.println("Unknown exception: " + e);
			e.printStackTrace();
		}
		return result;
	}

	private static JsonNode executeCommand(JsonNode currentNode, String command) {
		JsonNode newNode = null;
		if (currentNode != null) {
			System.out.println("Node before " + currentNode);
			if (isGet(command)) {
				String argument = command.substring(4, command.length() - 1);
				int index = Integer.parseInt(argument);
				newNode = currentNode.get(index);
			} else {
				newNode = currentNode.get(command);
			}
			System.out.println("Node after " + newNode);
		}
		return newNode;
	}

	private static boolean isGet(String command) {
		Pattern p = Pattern.compile("get\\(\\d+\\)");
		Matcher m = p.matcher(command);
		return m.matches();
	}
}
