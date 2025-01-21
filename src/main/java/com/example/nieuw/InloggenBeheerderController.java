package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField; // Zorg dat deze import aanwezig is
import javafx.stage.Stage;

import java.io.IOException;

public class InloggenBeheerderController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    private final String dummyEmail = "beheerder";
    private final String dummyPassword = "123";

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.equals(dummyEmail) && password.equals(dummyPassword)) {
            System.out.println("Succesvol ingelogd als beheerder!");

            try {
                // Laad het FXML-bestand voor Zoekpagina
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/zoekpagina.fxml"));
                Parent root = fxmlLoader.load();

                // Verkrijg de huidige stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Stel de nieuwe scene in
                stage.setScene(new Scene(root));
                stage.setTitle("Zoekpagina");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Onjuiste inloggegevens voor beheerder.");
        }
    }

    @FXML
    private void switchToInloggenKlant(javafx.event.ActionEvent event) {
        try {
            // Laad het FXML-bestand voor Inloggen Klant
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/inloggen Klant.fxml"));
            Parent root = fxmlLoader.load();

            // Verkrijg de huidige stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Stel de nieuwe scene in
            stage.setScene(new Scene(root));
            stage.setTitle("Inloggen Klant");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
