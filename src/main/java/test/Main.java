package test;

import entities.Personne;
import services.ServicePersonne;
import utils.MyDB;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MyDB conn1 = MyDB.getInstance();


        Personne p1 = new Personne(22,"Ben Slimene","Kenza");
        Personne p2 = new Personne(23,"Msahli","ALi");
        Personne p3 = new Personne(2,25,"Achouri","Mariem");


        ServicePersonne s = new ServicePersonne();
        try {
            s.ajouter(p1);
            s.ajouter(p2);
            s.ajouter(p3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(s.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            s.modifier(p3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
