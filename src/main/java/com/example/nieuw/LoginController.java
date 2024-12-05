package com.example.nieuw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField tf_gebruikersnaam;

    @FXML
    private PasswordField tf_wachtwoord;

    @FXML
    private Button bt_inloggen;

    @FXML
    private Label labelError;

    @FXML
    private void handleInloggen() {
        String gebruikersnaam = tf_gebruikersnaam.getText();
        String wachtwoord = tf_wachtwoord.getText();

        // Dummy-inloggegevens
        String juisteGebruikersnaam = "admin";
        String juistWachtwoord = "1234";

        if (gebruikersnaam.isEmpty() || wachtwoord.isEmpty()) {
            labelError.setText("Vul alle velden in.");
        } else if (gebruikersnaam.equals(juisteGebruikersnaam) && wachtwoord.equals(juistWachtwoord)) {
            try {
                // Laad zoekpagina.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("zoekpagina.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) bt_inloggen.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Zoekpagina");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            labelError.setText("Onjuiste gebruikersnaam of wachtwoord.");
        }
    }
}
