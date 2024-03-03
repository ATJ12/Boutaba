package services;

import entities.Personne;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePersonne implements IService<Personne> {

    private Connection con;

    public ServicePersonne(){
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Personne personne) throws SQLException {
     String req = "insert into personne (nom,prenom,age)"+
             "values ('"+personne.getNom()+"','"+personne.getPrenom()+"',"+personne.getAge()+")";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(Personne personne, int id) throws SQLException {

    }

    public void modifier(Personne personne) throws SQLException {
        String req = "update personne set nom=? , prenom=? , age=? where id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,personne.getNom());
        pre.setString(2,personne.getPrenom());
        pre.setInt(3,personne.getAge());
        pre.setInt(4,personne.getId());

        pre.executeUpdate();


    }

    @Override
    public boolean supprimer(int id) {

        return false;
    }


    @Override
    public List<Personne> afficher() throws SQLException {
        List<Personne> pers = new ArrayList<>();

        String req = "select * from personne";
        PreparedStatement pre = con.prepareStatement(req);
       ResultSet res= pre.executeQuery();
       while (res.next()){
           Personne p = new Personne();
           p.setId(res.getInt(1));
           p.setNom(res.getString("nom"));
           p.setPrenom(res.getString(3));
           p.setAge(res.getInt("age"));
           pers.add(p);
       }


        return pers;
    }
}
