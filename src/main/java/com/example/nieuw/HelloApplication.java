package com.example.nieuw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Laad de FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PaalToevoegen.fxml"));
        Parent root = fxmlLoader.load();

        // Stel full-screen resolutie in
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        Scene scene = new Scene(root, screenWidth, screenHeight);

        // Maak de stage resizable en stel automatische aanpassing in
        stage.setResizable(true);
        stage.setMaximized(true);

        stage.setTitle("Paal Toevoegen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
