package controllers;

import entities.Job;
import entities.Personne;
import entities.Recruteur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceRec;
import services.Servicejob;
import utils.MyDB;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AffichageJob {
    Servicejob servicejob= new Servicejob();
    @FXML
    private TableView<Job> jobTableView;
    @FXML
    private TableColumn<Job, Integer> tfidjob;
    @FXML
    private TableColumn<Job,String> tfnomjob;
    @FXML
    private TableColumn<Job,String> tfspecialite;
    @FXML
    private TableColumn<Job,Integer> tfidrecruter;
    @FXML
    private TableColumn<Job,Integer> tfsalaire;
    @FXML
    private TextField job_name;
    @FXML
    private ComboBox<String>specialite;
    @FXML
    private TextField salaire;
    @FXML
    private TextField idrecruter;
    @FXML
    private Button delete;
    @FXML
    private Button update;

    ObservableList<String> options = FXCollections.observableArrayList(
            "data science",
            "genie logiciel",
            "IOT",
            "Win",
            "IA",
            "business intelligence"
    );
    @FXML
    private TextField searchTextField;

    ObservableList <Job> liste = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        try {
            // Initialize
            // TableView columns
            ObservableList<Job> observableList = FXCollections.observableList(servicejob.afficher());
            FilteredList<Job> data = new FilteredList<>(observableList, b -> true);

            tfidjob.setCellValueFactory(new PropertyValueFactory<>("idjob"));
            tfnomjob.setCellValueFactory(new PropertyValueFactory<>("nom_job"));
            tfspecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            tfidrecruter.setCellValueFactory(new PropertyValueFactory<>("idrecuteur"));
            tfsalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
            specialite.setItems(options);
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                data.setPredicate(job -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    // Adjust these conditions based on the attributes of your Job class
                    if (job.getNom_job() != null && job.getNom_job().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (job.getSpecialite() != null && job.getSpecialite().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (String.valueOf(job.getSalaire()).toLowerCase().equals(lowerCaseFilter)) {
                        return true;
                    }
                    if (String.valueOf(job.getIdrecuteur()).toLowerCase().equals(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            jobTableView.setItems(data); // Set the TableView items to the filtered list


        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }



    }
    int id_selected;

    public void inc_clicked(javafx.scene.input.MouseEvent mouseEvent) { System.out.println("Clicked on " + jobTableView.getSelectionModel().getSelectedItem().getIdrecuteur());
        delete.setDisable(false);
        int index = jobTableView.getSelectionModel().getSelectedItem().getIdjob();
        ServiceRec T = new ServiceRec();
        update.setDisable(false);

        job_name.setText(jobTableView.getSelectionModel().getSelectedItem().getNom_job());
        specialite.setValue(jobTableView.getSelectionModel().getSelectedItem().getSpecialite());
        idrecruter.setText(String.valueOf(jobTableView.getSelectionModel().getSelectedItem().getIdrecuteur()));
        salaire.setText(String.valueOf(jobTableView.getSelectionModel().getSelectedItem().getSalaire()));

        //   numtel.setText(String.valueOf(recruitersTable.getSelectionModel().getSelectedItem().getNumtel()));
     //   int numtelValue = Integer.parseInt(numtel.getText());


       // id_selected=recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur();
    }
    @FXML
    private void deleteT(javafx.event.ActionEvent event) throws IOException {
        Servicejob T = new Servicejob();

        System.out.println(jobTableView.getSelectionModel().getSelectedItem().getIdjob() + "");
        try {
            T.supprimer(jobTableView.getSelectionModel().getSelectedItem().getIdjob());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Job deleted");
            alert.setContentText("Job has been deleted");
            alert.showAndWait();

            JOptionPane.showMessageDialog(null, "Data avec succès!");
            Job selectedItem = jobTableView.getSelectionModel().getSelectedItem();

            // Remove the item from the ObservableList
            jobTableView.getItems().remove(selectedItem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e.getMessage());
        }
    }


    @FXML
    private void modifierJob(javafx.event.ActionEvent event) {
        if (!isValidName(job_name.getText())) {
            showErrorAlert("Invalid Name", "Please enter a name without numbers.");
            return;
        }

        try {
            String nomJob = job_name.getText();
            String sep = specialite.getValue();
            float sal = Float.parseFloat(salaire.getText());
            int idRecruteur = Integer.parseInt(idrecruter.getText());

            Job modifiedJob = new Job(nomJob, sep, sal, idRecruteur);
            int modifiedJobId = jobTableView.getSelectionModel().getSelectedItem().getIdjob();

            // Call the service to modify the job in the database
            servicejob.modifier(modifiedJob, modifiedJobId);

            // Refresh TableView
            ObservableList<Job> updatedList = FXCollections.observableList(servicejob.afficher());
            jobTableView.setItems(updatedList);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "Job Modified", "The job has been modified successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Input", "Please enter valid numeric values for Salary and Recruteur ID.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while modifying the job in the database.");
        }
    }





    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
    ObservableList<Job> list = FXCollections.observableArrayList(); // Declare list here

    @FXML
    private void Trie_asc(javafx.event.ActionEvent event) throws SQLException {
               try{
                List<Job> jobs;
                jobs = servicejob.Trie();


                ObservableList<Job> jobList = FXCollections.observableList(jobs);

                tfidjob.setCellValueFactory(new PropertyValueFactory<>("idjob"));
                tfnomjob.setCellValueFactory(new PropertyValueFactory<>("nom_job"));
                tfspecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
                tfidrecruter.setCellValueFactory(new PropertyValueFactory<>("idrecuteur"));
                tfsalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

                jobTableView.setItems(jobList);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception as needed (log, show an alert, etc.)
            }

    }
    @FXML
    private void Trie_des(javafx.event.ActionEvent event) throws SQLException {
        try{
            List<Job> jobs;
            jobs = servicejob.TrieDES();


            ObservableList<Job> jobList = FXCollections.observableList(jobs);

            tfidjob.setCellValueFactory(new PropertyValueFactory<>("idjob"));
            tfnomjob.setCellValueFactory(new PropertyValueFactory<>("nom_job"));
            tfspecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            tfidrecruter.setCellValueFactory(new PropertyValueFactory<>("idrecuteur"));
            tfsalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

            jobTableView.setItems(jobList);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed (log, show an alert, etc.)
        }

    }



    }

