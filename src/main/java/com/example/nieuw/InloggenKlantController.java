package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InloggenKlantController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    private final String dummyEmail = "klant";
    private final String dummyPassword = "123";

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.equals(dummyEmail) && password.equals(dummyPassword)) {
            System.out.println("Succesvol ingelogd als klant!");
            try {
                // Laad zoekpaginaKlant.fxml na succesvol inloggen
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/zoekpaginaKlant.fxml"));
                Parent root = fxmlLoader.load();

                // Verkrijg de huidige stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Stel de nieuwe scene in
                stage.setScene(new Scene(root));
                stage.setTitle("Zoekpagina Klant");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Onjuiste inloggegevens voor klant.");
        }
    }

    @FXML
    private void switchToInloggenBeheerder(javafx.event.ActionEvent event) {
        try {
            // Schakel over naar Inloggen Beheerder
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/inloggen Beheerder.fxml"));
            Parent root = fxmlLoader.load();

            // Verkrijg de huidige stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Stel de nieuwe scene in
            stage.setScene(new Scene(root));
            stage.setTitle("Inloggen Beheerder");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
