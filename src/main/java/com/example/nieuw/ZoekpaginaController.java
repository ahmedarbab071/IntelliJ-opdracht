package com.example.nieuw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZoekpaginaController {

    @FXML
    private ListView<String> straatListView;

    @FXML
    private ListView<String> paalListView;

    private ObservableList<String> straatList = FXCollections.observableArrayList();
    private ObservableList<String> paalList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Laad de lijst met straten bij het opstarten van de pagina
        laadStraten();
    }

    private void laadStraten() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT straatnaam FROM straat";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            straatList.clear();
            while (resultSet.next()) {
                straatList.add(resultSet.getString("straatnaam"));
            }

            straatListView.setItems(straatList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStraatClick() {
        String geselecteerdeStraat = straatListView.getSelectionModel().getSelectedItem();
        if (geselecteerdeStraat != null) {
            laadPalen(geselecteerdeStraat);
        }
    }

    private void laadPalen(String straatnaam) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT paalnummer FROM paal p " +
                    "JOIN straat s ON p.straat_id = s.straat_id " +
                    "WHERE s.straatnaam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, straatnaam);
            ResultSet resultSet = statement.executeQuery();

            paalList.clear();
            while (resultSet.next()) {
                paalList.add(resultSet.getString("paalnummer"));
            }

            paalListView.setItems(paalList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePaalClick(MouseEvent event) {
        String geselecteerdePaal = paalListView.getSelectionModel().getSelectedItem();
        if (geselecteerdePaal != null) {
            toonPaalDetails(event, geselecteerdePaal);
        }
    }

    private void toonPaalDetails(MouseEvent event, String paalnummer) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/paaldetails.fxml"));
            Parent root = fxmlLoader.load();

            // Geef de geselecteerde paal door aan de PaalDetailsController
            PaalDetailsController controller = fxmlLoader.getController();
            controller.setPaalnummer(paalnummer);

            // Toon de details in een nieuw venster
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Paal Details");
            stage.show();

            // Optioneel: sluit de huidige zoekpagina als je dat wilt
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToPaalToevoegen(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/nieuw/paaltoevoegen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Paal Toevoegen");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
