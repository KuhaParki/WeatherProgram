package org.example.weatherapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is the "back-end" class that fetches the data from the web by a url address.
 */
public class WeatherService {

    // Defining a string for the api-key.
    private static final String API_KEY = System.getenv("WEATHER_API_KEY");

    // Defining a string for the base url that will be used
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    /**
     * Fetches the weather data for the given city.
     *
     * @param city The city name for which the weather data is to be fetched.
     * @return JsonNode containing the weather data.
     * @throws Exception If an error occurs while fetching the weather data.
     */
    public static JsonNode getWeather(String city) throws Exception {

        // Check to see if te api key is not null
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API key is not set in environment variables.");
        }

        // Defining a string for the whole url address
        String urlString = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";

        // Constructing a URL-object with the string and then opening the connection
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Handling a situation where the city that is being fetched doesn't exist.
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to fetch weather data: HTTP error code : " + responseCode);
        }

        // Reading the data and adding it to the StringBuilder object.
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content.toString());
    }
}

