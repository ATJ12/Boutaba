package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import utils.MyDB;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.imageio.ImageIO;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jobchart {
    @FXML
    private PieChart pieChart;
    ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

    Map<String, Integer> hm = new HashMap<String, Integer>();
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection con = MyDB.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT specialite, COUNT(idjob) as c FROM job GROUP BY specialite");

            while (rs.next()) {
                list.add(new PieChart.Data(rs.getString("specialite"), rs.getInt("c")));
                System.out.println("n");

            }

            pieChart.setData(list);

        } catch (SQLException ex) {
            Logger.getLogger(Jobchart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Stage stage;
    private Scene scene;
    @FXML
    private void StatTournoi(ActionEvent event) {
        list.clear();
        pieChart.setVisible(true);
        pieChart.setDisable(false);


        try{

            Connection con = MyDB.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT specialite, COUNT(idjob) as c FROM job GROUP BY specialite");
            while(rs.next()){
                list.add(new PieChart.Data(rs.getString("Rank"),rs.getInt(2)));
            }
            pieChart.setData(list);
        } catch (SQLException ex) {
            Logger.getLogger(Jobchart.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void StatTournoi(javafx.event.ActionEvent actionEvent) {
        list.clear();
        pieChart.setVisible(true);
        pieChart.setDisable(false);


        try{

            Connection con = MyDB.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT specialite, COUNT(idjob) as c FROM job GROUP BY specialite");
            while(rs.next()){
                list.add(new PieChart.Data(rs.getString("specialite"),rs.getInt(2)));
            }
            pieChart.setData(list);
        } catch (SQLException ex) {
            Logger.getLogger(Jobchart.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static int numeroPDF = 0;
    Document doc;

    @FXML
    private void pdf_ch(MouseEvent event) {
        numeroPDF = numeroPDF + 1;
        String nom = "Graph statistique " + numeroPDF + ".pdf";
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat Heure = new SimpleDateFormat("hh:mm:ss");

            WritableImage wimg = pieChart.snapshot(new SnapshotParameters(), null);

            BufferedImage bufferedImage = new BufferedImage((int) wimg.getWidth(), (int) wimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = wimg.getPixelReader();
            for (int x = 0; x < wimg.getWidth(); x++) {
                for (int y = 0; y < wimg.getHeight(); y++) {
                    bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
                }
            }

            File file = new File("ChartSnapshot.png");
            ImageIO.write(bufferedImage, "png", file);

            PdfWriter writer = PdfWriter.getInstance((com.itextpdf.text.Document) doc, new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\" + nom));
            ((com.itextpdf.text.Document) doc).open();
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("ChartSnapshot.png");
            ((com.itextpdf.text.Document) doc).add(img);
            ((com.itextpdf.text.Document) doc).close();
            Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Desktop\\" + nom));
            writer.close();

        } catch (Exception e) {
            System.out.println("Error PDF");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    public void pdf_ch(javafx.scene.input.MouseEvent mouseEvent) {

        numeroPDF = numeroPDF + 1;
        String nom = "Graph statistique " + numeroPDF + ".pdf";
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat Heure = new SimpleDateFormat("hh:mm:ss");

            WritableImage wimg = pieChart.snapshot(new SnapshotParameters(), null);

            BufferedImage bufferedImage = new BufferedImage((int) wimg.getWidth(), (int) wimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = wimg.getPixelReader();
            for (int x = 0; x < wimg.getWidth(); x++) {
                for (int y = 0; y < wimg.getHeight(); y++) {
                    bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
                }
            }

            File file = new File("ChartSnapshot.png");
            ImageIO.write(bufferedImage, "png", file);

            PdfWriter writer = PdfWriter.getInstance((com.itextpdf.text.Document) doc, new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\" + nom));
            ((com.itextpdf.text.Document) doc).open();
            com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("ChartSnapshot.png");
            ((com.itextpdf.text.Document) doc).add(img);
            ((com.itextpdf.text.Document) doc).close();
            Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Desktop\\" + nom));
            writer.close();

        } catch (Exception e) {
            System.out.println("Error PDF");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }

    }
}

