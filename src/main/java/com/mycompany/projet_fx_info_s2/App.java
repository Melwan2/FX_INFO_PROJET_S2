package com.mycompany.projet_fx_info_s2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        var javaVersion = SystemInfo.javaVersion();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        
         pane.add(new Label("Cx:"), 0, 2);
        TextField cx = new TextField();
        pane.add(cx, 1, 2);
        pane.add(new Label("Cy:"), 0, 3);
        TextField cy = new TextField();
        pane.add(cy, 1, 3);
        
        ArrayList<Coin> liste_Coins = new ArrayList<Coin>();
        
         Button btAdd = new Button("Ajouter Coin");
        pane.add(btAdd, 0, 5);
          btAdd.setOnAction(evt -> {
            Coin c1 = new Coin(Double.parseDouble(cx.getText()),
                              Double.parseDouble(cy.getText()));
            liste_Coins.add(c1);
            // Calcul et affichage de la surface
           
            System.out.println("Coin : "   + cx.getText() + "," + cy.getText()+") ajouté à la liste");
        });

        Button btSave = new Button("Sauvegarder Coins");
        pane.add(btSave, 1, 5);
        btSave.setOnAction(evt -> {
            PrintWriter pw;
            try {
                pw = new PrintWriter(new FileOutputStream("Coins.txt"));
                for (Coin c1 : liste_Coins)
                    pw.println("Coin;"+c1.getOri_x()+";"+c1.getOri_y());
                pw.close();
                System.out.println("Coin enregistré dans le fichier Coins.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }            
        });
        
    }
    

    public static void main(String[] args) {
        launch();
    }

}