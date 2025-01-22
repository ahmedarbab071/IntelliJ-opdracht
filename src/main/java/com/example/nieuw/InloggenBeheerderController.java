package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InloggenBeheerderController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        String query = "SELECT * FROM challengetest.gebruiker WHERE gebruikersnaam = ? AND wachtwoord = ? ";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Succesvol ingelogd als beheerder!");

                // Laad zoekpagina.fxml
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/zoekpagina.fxml"));
                Parent root = fxmlLoader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.setTitle("Zoekpagina Beheerder");
                stage.show();
            } else {
                System.out.println("Onjuiste inloggegevens voor beheerder.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToInloggenKlant(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/inloggen Klant.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Inloggen Klant");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
