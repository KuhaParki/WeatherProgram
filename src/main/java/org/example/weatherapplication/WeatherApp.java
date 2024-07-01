package org.example.weatherapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.JsonNode;

public class WeatherApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter city name");

        Label weatherLabel = new Label();

        Button getWeatherButton = new Button("Get Weather");
        getWeatherButton.setOnAction(e -> {
            String city = cityInput.getText();
            try {
                JsonNode weatherData = WeatherService.getWeather(city);
                double temp = weatherData.get("main").get("temp").asDouble();
                String description = weatherData.get("weather").get(0).get("description").asText();
                double wind = weatherData.get("wind").get("speed").asDouble();
                weatherLabel.setText("Temperature: " + temp + "°C\nDescription: " + description + "\nWind speed: " +
                        wind + "m/s");
            } catch (Exception ex) {
                weatherLabel.setText("Error fetching weather data.");
            }
        });

        VBox root = new VBox(10, cityInput, getWeatherButton, weatherLabel);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

