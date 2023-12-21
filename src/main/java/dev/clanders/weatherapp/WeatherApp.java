package dev.clanders.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WeatherApp extends Application {
    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/WeatherApp.fxml")));

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
