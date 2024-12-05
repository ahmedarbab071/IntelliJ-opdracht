package com.example.nieuw;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;

public class PaalToevoegenController {

    @FXML
    private VBox formulierVBox;

    @FXML
    private TextField paalnummerField;

    @FXML
    private TextField locatieField;

    @FXML
    private TextField datumField;

    @FXML
    private Button opslaanButton;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private void handleOpslaanButtonClick() {
        String paalnummer = paalnummerField.getText();
        String locatie = locatieField.getText();
        String datum = datumField.getText();

        if (paalnummer.isEmpty() || locatie.isEmpty() || datum.isEmpty()) {
            showAlert("Fout", "Vul alle velden in.", Alert.AlertType.ERROR);
        } else {
            showAlert("Succes", "Gegevens succesvol opgeslagen!", Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Observeer de breedte van het parentPane
        parentPane.widthProperty().addListener((observable, oldWidth, newWidth) -> {
            formulierVBox.setPrefWidth(newWidth.doubleValue() * 0.6); // 60% van de breedte
        });
    }
}
