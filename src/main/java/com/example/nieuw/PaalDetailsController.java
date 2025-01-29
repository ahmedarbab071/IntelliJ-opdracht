package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaalDetailsController {

    @FXML
    private Label paalnummerLabel;

    @FXML
    private Label accupercentageLabel;

    @FXML
    private Label accuverbruikLabel;

    @FXML
    private Label accuopbrengstLabel;

    @FXML
    private Label gebruiksfrequentieLabel;

    @FXML
    private Label gebruiksmomentenLabel;

    private String paalnummer;

    // Methode om de paalnummer in te stellen
    public void setPaalnummer(String paalnummer) {
        this.paalnummer = paalnummer;
        laadPaalDetails();
        laadGebruiksmomenten();
        updateGebruiksfrequentie(); // Zorgt ervoor dat gebruiksfrequentie correct is
    }

    // Methode om paaldetails op te halen uit de database
    private void laadPaalDetails() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT paalnummer, accupercentage, accuverbruik, accuopbrengst FROM paal WHERE paalnummer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, paalnummer);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                paalnummerLabel.setText("Paalnummer: " + resultSet.getString("paalnummer"));
                accupercentageLabel.setText("Accupercentage: " + resultSet.getInt("accupercentage") + "%");
                accuverbruikLabel.setText("Accuverbruik: " + resultSet.getInt("accuverbruik") + " kWh");
                accuopbrengstLabel.setText("Accuopbrengst: " + resultSet.getInt("accuopbrengst") + " kWh");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode om gebruiksmomenten op te halen uit de database
    private void laadGebruiksmomenten() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT datum, tijdstip FROM gebruiksmoment WHERE paalnummer = ? ORDER BY datum DESC, tijdstip DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, paalnummer);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder gebruiksmomenten = new StringBuilder();
            int count = 0;

            while (resultSet.next()) {
                String datum = resultSet.getString("datum");
                String tijdstip = resultSet.getString("tijdstip");
                gebruiksmomenten.append("- ").append(datum).append(" om ").append(tijdstip).append("\n");
                count++;
            }

            if (gebruiksmomenten.length() > 0) {
                gebruiksmomentenLabel.setText(gebruiksmomenten.toString());
            } else {
                gebruiksmomentenLabel.setText("Geen gebruiksmomenten gevonden.");
            }

            gebruiksfrequentieLabel.setText("Gebruiksfrequentie: " + count + " keer");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode om gebruiksfrequentie correct bij te werken op basis van de database
    private void updateGebruiksfrequentie() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS frequentie FROM gebruiksmoment WHERE paalnummer = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, paalnummer);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int gebruiksfrequentie = resultSet.getInt("frequentie");
                gebruiksfrequentieLabel.setText("Gebruiksfrequentie: " + gebruiksfrequentie + " keer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode om terug te navigeren naar zoekpagina.fxml
    @FXML
    private void switchToZoekpagina(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("zoekpagina.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
