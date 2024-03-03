package entities;

import javafx.beans.binding.BooleanExpression;

import java.util.Objects;

public class Recruteur {
    private int idrecuteur;
    private String nom_recruteur;
    private String prenom_recruteur;
    private String nom_societe;
    private String email;
    private String pwd;
    private int numtel;


    public Recruteur(String nom_recruteur,String prenom_recruteur,String nom_societe,String email,String pwd,int numtel) {
        this.nom_recruteur = nom_recruteur ;
        this.prenom_recruteur = prenom_recruteur;
        this.nom_societe =nom_societe;
        this.email=email;
        this.numtel=numtel;
        this.pwd=pwd;

    }
    public Recruteur(String nom_recruteur,String prenom_recruteur,String nom_societe,String email,int numtel) {
        this.nom_recruteur = nom_recruteur ;
        this.prenom_recruteur = prenom_recruteur;
        this.nom_societe =nom_societe;
        this.email=email;
        this.numtel=numtel;

    }


    public int getIdrecuteur() {
        return idrecuteur;
    }

    public void setIdrecuteur(int idrecuteur) {
        this.idrecuteur = idrecuteur;
    }

    @Override
    public String toString() {
        return "Recruteur{" +
                "idrecuteur=" + idrecuteur +
                ", nom_recruteur='" + nom_recruteur + '\'' +
                ", prenom_recruteur='" + prenom_recruteur + '\'' +
                ", nom_societe='" + nom_societe + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", numtel='" + numtel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recruteur recruteur = (Recruteur) o;
        return idrecuteur == recruteur.idrecuteur && Objects.equals(nom_recruteur, recruteur.nom_recruteur) && Objects.equals(prenom_recruteur, recruteur.prenom_recruteur) && Objects.equals(nom_societe, recruteur.nom_societe) && Objects.equals(email, recruteur.email) && Objects.equals(pwd, recruteur.pwd) && Objects.equals(numtel, recruteur.numtel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idrecuteur, nom_recruteur, prenom_recruteur, nom_societe, email, pwd, numtel);
    }

    public String getNom_recruteur() {
        return nom_recruteur;
    }

    public String getPrenom_recruteur() {
        return prenom_recruteur;
    }

    public String getNom_societe() {
        return nom_societe;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNom_recruteur(String nom) {
        this.nom_recruteur = nom;
    }

    public void setPrenom_recruteur(String prenom) {
        this.prenom_recruteur = prenom;
    }

    public void setNom_societe(String nom_societe) {
        this.nom_societe = nom_societe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public Recruteur() {}


}
