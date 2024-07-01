package org.example.weatherapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is the Application class for the UI.
 */
public class WeatherApplication extends Application {

    /**
     * Main method
     * @param args Constant parameter
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start method for the UI
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Weather Application");
        primaryStage.show();
    }
}
