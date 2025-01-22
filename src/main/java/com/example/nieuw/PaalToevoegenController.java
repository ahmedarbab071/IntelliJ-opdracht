package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaalToevoegenController {

    @FXML
    private TextField paalnummerField;

    @FXML
    private TextField stadsnaamField;

    @FXML
    private TextField straatnaamField;

    @FXML
    private void handleOpslaanButtonClick() {
        String paalnummer = paalnummerField.getText();
        String stadsnaam = stadsnaamField.getText();
        String straatnaam = straatnaamField.getText();

        if (paalnummer.isEmpty() || stadsnaam.isEmpty() || straatnaam.isEmpty()) {
            showAlert("Fout", "Vul alle velden in!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Voeg stadsnaam toe aan `stad` als deze nog niet bestaat
            String stadSql = "INSERT IGNORE INTO stad (stadsnaam) VALUES (?)";
            try (PreparedStatement stadStatement = connection.prepareStatement(stadSql)) {
                stadStatement.setString(1, stadsnaam);
                stadStatement.executeUpdate();
            }

            // Voeg straat toe aan `straat` als deze nog niet bestaat
            String straatId = generateStraatId(connection, straatnaam);
            String straatSql = "INSERT IGNORE INTO straat (straat_id, straatnaam, stadsnaam) VALUES (?, ?, ?)";
            try (PreparedStatement straatStatement = connection.prepareStatement(straatSql)) {
                straatStatement.setString(1, straatId);
                straatStatement.setString(2, straatnaam);
                straatStatement.setString(3, stadsnaam);
                straatStatement.executeUpdate();
            }

            // Voeg paal toe aan `paal`
            String paalSql = "INSERT INTO paal (paalnummer, accupercentage, accuverbruik, accuopbrengst, gebruiksfrequentie, klant_id, straat_id) " +
                    "VALUES (?, 100, 50, 50, 0, 'k0001', ?)";
            try (PreparedStatement paalStatement = connection.prepareStatement(paalSql)) {
                paalStatement.setString(1, paalnummer);
                paalStatement.setString(2, straatId);
                paalStatement.executeUpdate();
            }

            connection.commit();
            showAlert("Succes", "De paal is succesvol toegevoegd!", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het opslaan van de gegevens.", Alert.AlertType.ERROR);
        }
    }

    private String generateStraatId(Connection connection, String straatnaam) throws SQLException {
        // Genereer een unieke straat_id gebaseerd op het aantal rijen in de tabel
        String query = "SELECT COUNT(*) FROM straat";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             var resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1) + 1;
                return String.format("s%04d", count);
            }
        }
        throw new SQLException("Kon geen unieke straat_id genereren.");
    }

    private void clearFields() {
        paalnummerField.clear();
        stadsnaamField.clear();
        straatnaamField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToHome(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("zoekpagina.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Zoekpagina");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
