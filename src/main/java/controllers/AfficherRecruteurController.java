package controllers;

import entities.Personne;
import entities.Recruteur;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceRec;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class AfficherRecruteurController {

    @FXML
    private TableView<Recruteur> recruitersTable;

    @FXML
    private TableColumn<Recruteur, Integer> colId;

    @FXML
    private TableColumn<Recruteur, String> colNom;

    @FXML
    private TableColumn<Recruteur, String> colPrenom;

    @FXML
    private TableColumn<Recruteur, String> colSociete;

    @FXML
    private TableColumn<Recruteur, String> colEmail;

    @FXML
    private TableColumn<Recruteur, Integer> colNumtel;

    private final ServiceRec serviceRec = new ServiceRec();
    @FXML
    private TextField name;
    @FXML
    private TextField email1;
    @FXML
    private TextField prenom;
    @FXML
    private TextField societe;
    @FXML
    private TextField numtel;
    @FXML
    private Button delete;
    @FXML
    private Button updateT;

    @FXML
    void initialize() {
        // Initialize TableView columns
        try {
            ObservableList<Recruteur> observableList = FXCollections.observableList(serviceRec.afficher());
            recruitersTable.setItems(observableList);
            colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdrecuteur()).asObject());
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom_recruteur"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom_recruteur"));
            colSociete.setCellValueFactory(new PropertyValueFactory<>("nom_societe"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            colNumtel.setCellValueFactory(new PropertyValueFactory<>("Numtel"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

    }
    ServiceRec serviceRecruteur = new ServiceRec();
    @FXML
    private void deleteT(javafx.event.ActionEvent event)throws IOException {
        ServiceRec T = new ServiceRec();

        System.out.println(recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur()+"");
        try{
            T.supprimer(recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User deleted");
            alert.setContentText("User Has Been deleted");
            alert.showAndWait();

            JOptionPane.showMessageDialog(null, "Data avec succée!");
            Recruteur selectedItem = recruitersTable.getSelectionModel().getSelectedItem();
            recruitersTable.getItems().remove(selectedItem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error"+e.getMessage());

        }
    }

    int id_selected;
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void modifierRecruteur(javafx.event.ActionEvent event) {
        int index = id_selected;
        ServiceRec T = new ServiceRec();
        Recruteur recruteur = new Recruteur(name.getText().toString(), prenom.getText().toString(), societe.getText().toString(), email1.getText().toString(), Integer.parseInt(numtel.getText()));
        if (!isValidEmail(email1.getText())) {
            showErrorAlert("Invalid Email", "Please enter a valid email address.");
            return;
        }
        if (!isValidNumtel(Integer.parseInt(numtel.getText()))) {
            showErrorAlert("Invalid Numtel", "Please enter a valid phone number with the correct country code and length.");
            return;
        }
        if (!isValidName(name.getText())) {
            showErrorAlert("Invalid Name", "Please enter a name without numbers.");
            return;
        }if (!isValidPrenom(prenom.getText())) {
            showErrorAlert("Invalid prenom", "Please enter a prenom without numbers.");
            return;
        }
        if(name.getText().length()!=0 && societe.getText().length()!=0 && email1.getText().length()!=0 &&  numtel.getText().length()!=0) {
            try {
                int numtel1 = Integer.parseInt(numtel.getText());
                if (!isNumtelUnique(numtel1)) {
                    showErrorAlert("Duplicate Numtel", "The provided phone number is already in use.");
                    return;
                }
                T.modifier(recruteur, index);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User Modified");
                alert.setContentText("User Has Been Updated");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(AfficherRecruteurController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("success");
            alert.setContentText("Recruteur modifié");
            alert.show();

            // Assuming tvRecruteur is the TableView for Recruteurs, update it accordingly
            recruitersTable.getItems().clear();
            try {
                List<Recruteur> recruteurs = T.afficher();
                recruitersTable.getItems().addAll(recruteurs);
            } catch (SQLException ex) {
                Logger.getLogger(AfficherRecruteurController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: One or more fields are empty.");
        }
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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




    public void tableview_clicked(javafx.scene.input.MouseEvent mouseEvent) {
        delete.setDisable(false);
        updateT.setDisable(false);
        Recruteur selectedRecruteur = recruitersTable.getSelectionModel().getSelectedItem();

        colNom.setText(selectedRecruteur.getNom_recruteur());
        colPrenom.setText(selectedRecruteur.getPrenom_recruteur());
        colSociete.setText(selectedRecruteur.getNom_societe());
        colSociete.setText(selectedRecruteur.getEmail());
        colNumtel.setText(String.valueOf(selectedRecruteur.getNumtel()));
    }

    @FXML


    public void inc_clicked(javafx.scene.input.MouseEvent mouseEvent) { System.out.println("Clicked on " + recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur());
            delete.setDisable(false);
            int index = recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur();
            ServiceRec T = new ServiceRec();
            updateT.setDisable(false);

            name.setText(recruitersTable.getSelectionModel().getSelectedItem().getNom_recruteur());
            prenom.setText(recruitersTable.getSelectionModel().getSelectedItem().getPrenom_recruteur());
            societe.setText(recruitersTable.getSelectionModel().getSelectedItem().getNom_societe());
            email1.setText(recruitersTable.getSelectionModel().getSelectedItem().getEmail());
            numtel.setText(String.valueOf(recruitersTable.getSelectionModel().getSelectedItem().getNumtel()));
            int numtelValue = Integer.parseInt(numtel.getText());


        id_selected=recruitersTable.getSelectionModel().getSelectedItem().getIdrecuteur();
        }

}