package controllers;

import entities.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPersonneController {
    ServicePersonne servicePersonne= new ServicePersonne();

    @FXML
    private TextField tf_age;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prenom;

    @FXML
    void AfficherPersonne(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPersonne.fxml"));
            tf_prenom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @FXML
    void AjouterPErsonne(ActionEvent event) {
        try {
            servicePersonne.ajouter(new Personne(Integer.parseInt(tf_age.getText()),tf_nom.getText(),tf_prenom.getText()));
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("personne ajoute");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

}
