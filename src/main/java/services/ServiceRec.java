package services;
import entities.Recruteur;
import utils.MyDB;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceRec implements IService<Recruteur> {
    Connection con;
    Statement stm;


    public ServiceRec(){
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Recruteur recruteur) throws SQLException {
        String req = "insert into recruteur (nom,prenom,nom_societe,Email,pwd,Numtel)"+
                "VALUES ('" + recruteur.getNom_recruteur() + "','" + recruteur.getPrenom_recruteur() + "','" + recruteur.getNom_societe() + "','" + recruteur.getEmail() + "','" + recruteur.getPwd() + "','" + recruteur.getNumtel() + "')";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);

    }

    @Override
        public void modifier(Recruteur recruteur, int id) throws SQLException {
            String req = "UPDATE `recruteur` SET `nom`= ? ,`prenom`= ? ,`nom_societe`= ? ,`email`= ? ,`numtel`= ? WHERE `idrecuteur` = ?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setString(1, recruteur.getNom_recruteur());
                pre.setString(2, recruteur.getPrenom_recruteur());
                pre.setString(3, recruteur.getNom_societe());
                pre.setString(4, recruteur.getEmail());
                pre.setInt(5, recruteur.getNumtel());
                pre.setInt(6, id);

                pre.executeUpdate();
            }
        }

    @Override
    public boolean supprimer(int id) {
        String req = "DELETE FROM recruteur WHERE `idrecuteur` = "+ id;
        try {
            stm = con.createStatement();
        } catch (SQLException e) {
            // Handle the exception (print the stack trace, log, or take appropriate action)
            e.printStackTrace();
        }
        try {
            stm.executeUpdate(req);
        } catch (SQLException e) {
            // Handle the exception (print the stack trace, log, or take appropriate action)
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Recruteur> afficher() throws SQLException {
        List<Recruteur> pers = new ArrayList<>();

        String req = "select * from recruteur";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Recruteur p = new Recruteur();
            p.setIdrecuteur(res.getInt(1));
            p.setNom_recruteur(res.getString("nom"));
            p.setPrenom_recruteur(res.getString("prenom"));
           // System.out.println(p.getPrenom_recruteur());
            p.setNom_societe(res.getString("nom_societe"));
            p.setEmail(res.getString("Email"));
            p.setNumtel(res.getInt("Numtel"));
            pers.add(p);
        }


        return pers;
    }
    public boolean isNumtelExists(int numtel) throws SQLException {
        // Create a PreparedStatement to execute a SQL query
        try (PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM recruteur WHERE numtel = ?")) {
            preparedStatement.setInt(1, numtel);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    // If count is greater than 0, it means the numtel already exists
                    return count > 0;
                }
            }
        }

        return false;
    }
    public boolean doesRecruiterIdMatchEmail(int id, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM recruteur WHERE idrecuteur = ? AND Email = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }}
    public void sendWelcomeEmail(String toEmail) {
        // Sender's email ID and password
        final String username = "keylog159753@gmail.com";
        final String password = "nuvrqjdgbghycdfj";

        // Assuming you are sending email through Gmail
        String host = "smtp.gmail.com";

        // Set up properties for the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject("Welcome to Your Company!");

            // Now set the actual message
            message.setText("Dear Recruiter,\n\nYour account has been created successfully. Welcome to Your Company!");

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public Recruteur connect(String email, String password) throws SQLException {
        String query = "SELECT * FROM recruteur WHERE Email = ? AND pwd = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Recruiter found, create and return a Recruteur object
                    return new Recruteur(
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("nom_societe"),
                            resultSet.getString("Email"),
                            resultSet.getString("pwd"),
                            resultSet.getInt("Numtel")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // No recruiter found with the given email and password
        return null;
    }
    public int getRecruteurIdByEmail(String email) throws SQLException {
        String query = "SELECT idrecuteur FROM recruteur WHERE email = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idrecuteur");
                }
            }
        }
        return 0; // Return a default value (e.g., 0) if no idrecuteur is found
    }

}
