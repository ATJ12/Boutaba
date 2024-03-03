package controllers;

import entities.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Servicejob;

import java.sql.SQLException;

public class recruiterJobsController {
    @FXML
    private TableView<Job> jobTableView;

    @FXML
    private TableColumn<Job, Integer> colIdjob;

    @FXML
    private TableColumn<Job, String> colNom_job;

    @FXML
    private TableColumn<Job, String> colSpecialite;

    @FXML
    private TableColumn<Job, Float> colSalaire;

    @FXML
    private TextField searchTextField;

    private Servicejob serviceJob;
    private int loggedInRecruiterId;

    public void initialize(int recruiterId, Servicejob serviceJob) {
        this.loggedInRecruiterId = recruiterId;
        this.serviceJob = serviceJob;

        initJobTableView();
    }

    private void initJobTableView() {
        try {
            ObservableList<Job> observableList = FXCollections.observableList(serviceJob.afficher());
            FilteredList<Job> data = new FilteredList<>(observableList, b -> true);

            colIdjob.setCellValueFactory(new PropertyValueFactory<>("idjob"));
            colNom_job.setCellValueFactory(new PropertyValueFactory<>("nom_job"));
            colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
            colSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

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
                    return false;
                });
            });

            jobTableView.setItems(data); // Set the TableView items to the filtered list
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }
    }

    // Utility method to display error alerts
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
