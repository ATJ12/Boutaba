package controllers;

import entities.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServicePersonne;

import java.sql.SQLException;

public class AfficherPersonneController {
    ServicePersonne servicePersonne= new ServicePersonne();

    @FXML
    private TableColumn<Personne, Integer> col_age;

    @FXML
    private TableColumn<Personne, String> col_nom;

    @FXML
    private TableColumn<Personne, String> col_prenom;

    @FXML
    private TableView<Personne> tv_personnes;

    @FXML
    void initialize(){

        try {
            ObservableList<Personne> observableList= FXCollections.observableList(servicePersonne.afficher());
            tv_personnes.setItems(observableList);
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
            col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
