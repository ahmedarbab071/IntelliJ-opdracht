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

public class ZoekpaginaKlantController {

    @FXML
    private ListView<String> straatListView;

    @FXML
    private ListView<String> paalListView;

    private ObservableList<String> straatList = FXCollections.observableArrayList();
    private ObservableList<String> paalList = FXCollections.observableArrayList();
    private String klantId;

    public void setKlantId(String klantId) {
        this.klantId = klantId;
        laadStraten();
    }

    private void laadStraten() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT s.straatnaam FROM straat s " +
                    "JOIN paal p ON s.straat_id = p.straat_id " +
                    "WHERE p.klant_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, klantId);
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
            String query = "SELECT p.paalnummer FROM paal p " +
                    "JOIN straat s ON p.straat_id = s.straat_id " +
                    "WHERE s.straatnaam = ? AND p.klant_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, straatnaam);
            statement.setString(2, klantId);
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
            try {
                // Laad de detailspagina en stuur de paalnummer door
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nieuw/paaldetailsKlant.fxml"));
                Parent root = loader.load();

                // Haal de controller op en stel de paalnummer in
                PaalDetailsKlantController controller = loader.getController();
                controller.setPaalnummer(geselecteerdePaal);

                // Toon de nieuwe scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Paal Details Klant");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
