package controllers;

import entities.Recruteur; // Assuming Recruteur is the class for recruiters
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceRec; // Assuming ServiceRecruteur is the service for recruiters

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AjouterRecruteurController {
    ServiceRec serviceRecruteur = new ServiceRec();

    @FXML
    private TextField tf_nom_recruteur;

    @FXML
    private TextField tf_prenom_recruteur;

    @FXML
    private TextField tf_nom_societe;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_pwd;

    @FXML
    private TextField tf_numtel;
    @FXML
    private TextField tf_pwd_confirmation;


    @FXML
    void AjouterRecruteur(ActionEvent event) {

        if (!isValidEmail(tf_email.getText())) {
            showErrorAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }
        if (!isValidNumtel(Integer.parseInt(tf_numtel.getText()))) {
            showErrorAlert("Invalid Numtel", "Please enter a valid phone number with the correct country code and length.");
            return;
        }
        if (!isValidName(tf_nom_recruteur.getText())) {
            showErrorAlert("Invalid Name", "Please enter a name without numbers.");
            return;
        }if (!isValidPrenom(tf_prenom_recruteur.getText())) {
            showErrorAlert("Invalid prenom", "Please enter a prenom without numbers.");
            return;

        }


        try {
            int numtel = Integer.parseInt(tf_numtel.getText());
            String password = tf_pwd.getText();
            String confirmPassword = tf_pwd_confirmation.getText();
            if (!password.equals(confirmPassword)) {
                showErrorAlert("Password Mismatch", "Password and confirmation do not match.");
                return;
            }

            // Check if the numtel is unique before adding
            if (!isNumtelUnique(numtel)) {
                showErrorAlert("Duplicate Numtel", "The provided phone number is already in use.");
                return;
            }
            if (!isValidPassword(password)) {
                showErrorAlert("Invalid Password", "Please enter a valid password with at least one uppercase letter, one lowercase letter, one special character, and one digit and length at least 8.");
                return;
            }
            if (isNumtelUnique(numtel)) {
                serviceRecruteur.ajouter(new Recruteur(tf_nom_recruteur.getText(), tf_prenom_recruteur.getText(), tf_nom_societe.getText(), tf_email.getText(), tf_pwd.getText(), Integer.parseInt(tf_numtel.getText())));
                serviceRecruteur.sendWelcomeEmail(tf_email.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Recruteur ajouté");
            alert.showAndWait();
            showInformationAlert("Success", "Recruteur ajouté");}
        } catch (SQLException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidNumtel(int numtel) {
        String numtelString = String.valueOf(numtel);
        return numtelString.matches("^\\d{8}$");
    }


    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidName(String name) {
        // Check if the name contains any digits
        return !name.matches(".*\\d.*");
    }
    private boolean isValidPrenom(String Prenom) {
        // Check if the name contains any digits
        return !Prenom.matches(".*\\d.*");
    }
    private boolean isNumtelUnique(int numtel) throws SQLException {
        // Check if the numtel is unique in your database
        return !serviceRecruteur.isNumtelExists(numtel);
    }
    private boolean isValidPassword(String password) {
        // Check if the password contains at least one uppercase letter, one lowercase letter, one special character, and one digit
        return  password.length() >= 8 &&
                password.matches(".*[A-Z].*") && // At least one uppercase letter
                password.matches(".*[a-z].*") && // At least one lowercase letter
                password.matches(".*\\d.*") &&   // At least one digit
                password.matches(".*[!@#$%^&*()-_+=<>?/{}\\[\\],.:;].*"); // At least one special character
    }
}
