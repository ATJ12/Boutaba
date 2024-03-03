package controllers;

import entities.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.Servicejob;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterJob implements Initializable {


    Servicejob servicejob= new Servicejob();
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "data science",
                    "genie logiciel",
                    "IOT",
                    "Win",
                    "IA",
                    "business intelligence"
            );
    @FXML
    private TextField tfNomJob;
    @FXML
    private TextField tfSalaire;
    @FXML
    private ComboBox<String> tfSpecialite;
    @FXML
    private TextField tfIdRecruteur;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "data science",
                "genie logiciel",
                "IOT",
                "Win",
                "IA",
                "business intelligence"
        );
        tfSpecialite.setItems(options);
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void addJob() {
        if (!isValidName(tfNomJob.getText())) {
            showErrorAlert("Invalid Name", "Please enter a name without numbers.");
            return;
        }
        try {
            String nomJob = tfNomJob.getText();
            String specialite = tfSpecialite.getValue();
            String selectedSalaire = tfSalaire.getText();
            int salaire = Integer.parseInt(selectedSalaire); // Assuming salaire is an integer
            int idRecruteur = Integer.parseInt(tfIdRecruteur.getText()); // Assuming idRecruteur is an integer

            // Validate the input fields as needed

            // Call the service to add the job
            servicejob.ajouter(new Job(nomJob, specialite, salaire, idRecruteur));

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Job Added", "The job has been added successfully.");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please enter valid numeric values for Salary and Recruteur ID.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while adding the job to the database.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isValidName(String name) {
        // Check if the name contains any digits
        return !name.matches(".*\\d.*");
    }













}
