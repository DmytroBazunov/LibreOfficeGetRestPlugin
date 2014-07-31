package com.github.binnarywolf.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestClient {
	private final String USER_AGENT = "Mozilla/5.0";

	public String get(String url) {
		String response = null;
		try {
			URL obj = null;
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer serverResponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				serverResponse.append(inputLine);
				serverResponse.append("\n");
			}
			in.close();
			response=serverResponse.toString();
		} catch (MalformedURLException e) {
			response = "Wrong Url format";
		} catch (ProtocolException e) {
			response = "Error in the underlying protocol";
		//	e.printStackTrace();
		} catch (IOException e) {
			response = "Failed to set up connection";
		//	e.printStackTrace();
		}
		response=response.trim();
		return response;
	}
}
