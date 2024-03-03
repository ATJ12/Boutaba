package services;

import entities.Job;
import utils.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicejob implements IService<Job> {
    Connection con;
    Statement stm;


    public Servicejob() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Job job) throws SQLException {
        String req = "INSERT INTO job (nom_job, specialite, salaire, idrecuteur) " +
                "VALUES ('" + job.getNom_job() + "','" + job.getSpecialite() + "','" + job.getSalaire() + "','" + job.getIdrecuteur() + "')";

        Statement ste = con.createStatement();
        ste.executeUpdate(req);
        System.out.print(job);


    }

    @Override
    public void modifier(Job job, int id) throws SQLException {
        String req = "UPDATE `job` SET `nom_job`= ? ,`specialite`= ? ,`salaire`= ? , `idrecuteur` = ? WHERE `idjob` =?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, job.getNom_job());
            pre.setString(2, job.getSpecialite());
            pre.setFloat(3, job.getSalaire());
            pre.setInt(4, job.getIdrecuteur());
            pre.setInt(5, id);

            // Print values set in PreparedStatement
            System.out.println("Values set in PreparedStatement:");
            System.out.println("1: " + job.getNom_job());
            System.out.println("2: " + job.getSpecialite());
            System.out.println("3: " + job.getSalaire());
            System.out.println("4: " + job.getIdrecuteur());
            System.out.println("5: " + id);

            int affectedRows = pre.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);

            if (affectedRows > 0) {
                System.out.println("Job updated successfully.");
            } else {
                System.out.println("Job update failed. No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean supprimer(int id) {
        String req = "DELETE FROM job WHERE `idjob` = " + id;
        System.out.println("Job uis deleted.");

        try {
            stm = con.createStatement();
        } catch (SQLException e) {
            // Handle the exception (print the stack trace, log, or take appropriate action)
            e.printStackTrace();
        }
        try {
            stm.executeUpdate(req);
            System.out.println("Job is deleted.");
        } catch (SQLException e) {
            // Handle the exception (print the stack trace, log, or take appropriate action)
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Job> afficher() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String req = "SELECT * FROM job";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            Job p = new Job();
            Job job = new Job();
            job.setIdjob(res.getInt("idjob"));
            job.setNom_job(res.getString("nom_job"));
            job.setSpecialite(res.getString("specialite"));
            job.setSalaire(res.getFloat("salaire"));
            job.setIdrecuteur(res.getInt("idrecuteur"));
            jobs.add(job);
            System.out.println(job);
        }
        return jobs;
    }
    public List<Job> Trie() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String req = "SELECT * FROM `job` ORDER BY salaire ASC";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            Job job = new Job();
            job.setIdjob(res.getInt("idjob"));
            job.setNom_job(res.getString("nom_job"));
            job.setSpecialite(res.getString("specialite"));
            job.setSalaire(res.getFloat("salaire"));
            job.setIdrecuteur(res.getInt("idrecuteur"));
            jobs.add(job);
            System.out.println(job);
        }
        return jobs;
    }
    public List<Job> TrieDES() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String req = "SELECT * FROM `job` ORDER BY salaire DESC";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            Job job = new Job();
            job.setIdjob(res.getInt("idjob"));
            job.setNom_job(res.getString("nom_job"));
            job.setSpecialite(res.getString("specialite"));
            job.setSalaire(res.getFloat("salaire"));
            job.setIdrecuteur(res.getInt("idrecuteur"));
            jobs.add(job);
            System.out.println(job);
        }
        return jobs;
    }
    public List<Job> searchJobs(String searchTerm) throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String req = "SELECT * FROM `job` WHERE " +
                "`nom_job` LIKE ? OR " +
                "`specialite` LIKE ? OR " +
                "`salaire` LIKE ? OR " +
                "`idrecruteur` LIKE ?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            for (int i = 1; i <= 4; i++) {
                pre.setString(i, "%" + searchTerm + "%");
            }

            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    Job job = new Job(
                            res.getInt("idjob"),
                            res.getString("nom_job"),
                            res.getString("specialite"),
                            res.getFloat("salaire"),
                            res.getInt("idrecruteur")
                    );
                    jobs.add(job);
                }
            }
        }

        return jobs;
    }
    public List<Job> getJobsByRecruiterId(int recruiterId) throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String query = "SELECT * FROM job WHERE idrecuteur = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, recruiterId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Job job = new Job();
                    job.setIdjob(resultSet.getInt("idjob"));
                    job.setNom_job(resultSet.getString("nom_job"));
                    job.setNom_job(resultSet.getString("specialite"));
                    job.setNom_job(resultSet.getString("salaire"));
                    job.setNom_job(resultSet.getString("idrecuteur"));

                    jobs.add(job);
                    System.out.println(jobs);
                }
            }
        }
        System.out.println(jobs);
        return jobs;
    }
}
