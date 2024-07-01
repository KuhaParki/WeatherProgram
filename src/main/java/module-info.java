module org.example.weatherapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.example.weatherapplication to javafx.fxml;
    exports org.example.weatherapplication;
}