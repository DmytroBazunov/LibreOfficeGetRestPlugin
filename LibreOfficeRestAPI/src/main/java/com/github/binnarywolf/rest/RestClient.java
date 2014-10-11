package com.github.binnarywolf.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Class contains method for executing GET request to the remote server.
 * @author <a href="mailto:binnarywolf@gmail.com">Dmitriy Bazunov</a>
 */
public class RestClient
{
    /**
     * User agent used to execute GET request.
     */
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * Method execute GET request to the given URL.
     * @param aURL
     *        String contains the URL address to which make get request
     * @return Get request result if all OK or error message otherwise.
     */
    public String get(final String aURL)
    {
        String response = null;
        try {
            final URL obj = new URL(aURL);
            final HttpURLConnection connection = (HttpURLConnection) obj
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            final int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + aURL);
            System.out.println("Response Code : " + responseCode);
            final BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            final StringBuffer serverResponse = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                serverResponse.append(inputLine);
                serverResponse.append("\n");
            }
            in.close();
            response = serverResponse.toString();
        }
        catch (MalformedURLException e) {
            response = "Wrong Url format " + e.getLocalizedMessage();
            e.printStackTrace();
        }
        catch (ProtocolException e) {
            response = "Error in the underlying protocol "
                    + e.getLocalizedMessage();
            e.printStackTrace();
        }
        catch (IOException e) {
            response = "Failed to set up connection " + e.getLocalizedMessage();
            e.printStackTrace();
        }
        response = response.trim();
        return response;
    }
}
