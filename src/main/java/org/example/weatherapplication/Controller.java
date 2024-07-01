package org.example.weatherapplication;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * This class is the controller class for the UI.
 */
public class Controller {

    public Button weatherButton;
    public Label weatherInfo;
    public TextField cityTextField;

    /**
     * This method is called when the weather button is clicked
     * It fetches and displays the weather information for the entered city.
     * @param actionEvent The event triggered by clicking the button.
     * @throws Exception
     */
    public void getWeather(ActionEvent actionEvent) throws Exception {

        // A string variable is made from the text given in the TextField
        String city = cityTextField.getText().trim();

        // A check that the text contains only small and big letters and "-" characters
        if (city.isEmpty() || !city.matches("[a-öA-Ö\\s-]+")) {
            weatherInfo.setText("Invalid city name. Please enter a valid city name.");
            return;
        }
        try {

            // Fetching the data from the web by calling the getWeather method
            JsonNode weatherData = WeatherService.getWeather(city);
            double temp = weatherData.get("main").get("temp").asDouble();
            String description = weatherData.get("weather").get(0).get("description").asText();
            double wind = weatherData.get("wind").get("speed").asDouble();
            double feelsLike = weatherData.get("main").get("feels_like").asDouble();
            double tempMin = weatherData.get("main").get("temp_min").asDouble();
            double tempMax = weatherData.get("main").get("temp_max").asDouble();
            double pressure = weatherData.get("main").get("pressure").asDouble();
            double humidity = weatherData.get("main").get("humidity").asDouble();
            double windDeg = weatherData.has("wind") && weatherData.get("wind").has("deg") ? weatherData.get("wind").get("deg").asDouble() : 0.0;
            double windGust = weatherData.has("wind") && weatherData.get("wind").has("gust") ? weatherData.get("wind").get("gust").asDouble() : 0.0;
            double rain1 = weatherData.has("rain") && weatherData.get("rain").has("1h") ? weatherData.get("rain").get("1h").asDouble() : 0.0;
            double rain3 = weatherData.has("rain") && weatherData.get("rain").has("3h") ? weatherData.get("rain").get("3h").asDouble() : 0.0;
            double snow1 = weatherData.has("snow") && weatherData.get("snow").has("1h") ? weatherData.get("snow").get("1h").asDouble() : 0.0;
            double snow3 = weatherData.has("snow") && weatherData.get("snow").has("3h") ? weatherData.get("snow").get("3h").asDouble() : 0.0;
            double visibility = weatherData.has("visibility") ? weatherData.get("visibility").asDouble() : 0.0;

            // Set the Label's text to show the weather data in wanted format
            weatherInfo.setText("Weather information of " + city + ":\n\nTemperature: " + temp + "°C\nFeels like: " + feelsLike + "°C\nTemp min: " + tempMin +
                    "°C\nTemp max: " + tempMax + "°C\nDescription: " + description + "\nWind speed: " +
                    wind + "m/s\nWind gusts: " + windGust + "m/s\nWind degree: " + windDeg + "°\nRainfall 1h: " + rain1 +
                    "mm\nRainfall 3h: " + rain3 + "mm\nSnowfall 1h: " + snow1 +
                    "mm\nSnowfall 3h: " + snow3 + "mm\nHumidity: " + humidity + "%\nPressure: " + pressure + "hPa\nVisibility: " +
                    visibility + "m");

        // Exception handling
        } catch (IOException ex) {
            weatherInfo.setText("Error: Unable to retrieve weather data. Please check your network connection.");
        }
        catch (RuntimeException ex) {
            weatherInfo.setText("Error: " + ex.getMessage());
        }
        catch (Exception ex) {
            weatherInfo.setText("An unexpected error occurred. Please try again later");
        }
    }
}
