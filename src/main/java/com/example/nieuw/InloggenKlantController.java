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

public class InloggenKlantController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private void handleLogin(javafx.event.ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        String query = "SELECT * FROM challengetest.klant WHERE naam = ? AND wachtwoord = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Succesvol ingelogd als klant!");

                // Laad zoekpaginaKlant.fxml
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/zoekpaginaKlant.fxml"));
                Parent root = fxmlLoader.load();

                // Haal de juiste controller op en geef klantgegevens door
                ZoekpaginaKlantController controller = fxmlLoader.getController();
                controller.setKlantId(rs.getString("klant_id"));  // Voorbeeld ID ophalen

                // Toon de nieuwe scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Zoekpagina klant");
                stage.show();

            } else {
                System.out.println("Onjuiste inloggegevens voor klant.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToInloggenBeheerder(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/inloggen Beheerder.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Inloggen Beheerder");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
