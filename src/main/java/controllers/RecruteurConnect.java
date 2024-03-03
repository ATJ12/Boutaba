package controllers;
import entities.Job;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Recruteur;  // Import your RecruteurService class
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.ServiceRec;
import services.Servicejob;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecruteurConnect {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    private Servicejob serviceJob;
    private int loggedInRecruiterId;
    public RecruteurConnect() {
        this.serviceJob = new Servicejob(); // Initialize serviceJob
    }

    @FXML
    private TableColumn<Job, Integer> colIdjob;

    @FXML
    private TableColumn<Job, String> colNom_job;

    @FXML
    private TableColumn<Job, String> colSpecialite;
    @FXML
    private TableColumn<Job, String> colsalaire;
    @FXML
    private Label timeLabel;

    public void initialize(int recruiterId, Servicejob serviceJob) {
        this.loggedInRecruiterId = recruiterId;
        this.serviceJob = serviceJob;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTime));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        // Initialize TableView
    }
    private void updateTime(ActionEvent event) {
        // Get the current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());

        // Update the Label with the current time
        timeLabel.setText("Current Time: " + currentTime);
    }

    // Other existing methods...



    private ServiceRec recruteurService = new ServiceRec(); // Initialize your RecruteurService

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Recruteur recruteur = recruteurService.connect(email, password);

            if (recruteur != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecruiterJobs.fxml"));
                Parent root = loader.load();

                recruiterJobsController RecruiterJobsController = loader.getController();
                RecruiterJobsController.initialize(recruteurService.getRecruteurIdByEmail(email),serviceJob);
                System.out.println(recruteur.getIdrecuteur());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                // Login successful, perform necessary actions
                showInformationAlert("Login Successful", "Welcome, " + recruteur.getNom_recruteur());
            } else {
                // Login failed, show an error alert
                showErrorAlert("Login Failed", "Invalid email or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while processing your request");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to display information alerts
    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Utility method to display error alerts
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    private void handleCreateAccount(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterRecruteur.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any other exceptions
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
    }



}
