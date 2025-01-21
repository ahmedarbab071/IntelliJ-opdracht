package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PaalToevoegenController {

    @FXML
    private TextField paalnummerField;

    @FXML
    private TextField locatieField;

    @FXML
    private TextField datumField;

    @FXML
    private void handleOpslaanButtonClick() {
        // Log de ingevoerde gegevens
        System.out.println("Paalnummer: " + paalnummerField.getText());
        System.out.println("Locatie: " + locatieField.getText());
        System.out.println("Datum: " + datumField.getText());
    }

    @FXML
    private void switchToHome(MouseEvent event) {
        try {
            // Schakel over naar Zoekpagina.fxml
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
