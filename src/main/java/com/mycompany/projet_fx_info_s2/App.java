package com.mycompany.projet_fx_info_s2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class App extends Application {

    @Override
    public void start(Stage primaryStage) { 
        
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        
        // Place nodes in the pane at positions column,row
        pane.add(new Label("Longueur:"), 0, 0);
        TextField lon = new TextField();
        pane.add(lon, 1, 0);
        pane.add(new Label("Largeur:"), 0, 1);
        TextField lar = new TextField();
        pane.add(lar, 1, 1);
        pane.add(new Label("Cx:"), 0, 2);
        TextField cx = new TextField();
        pane.add(cx, 1, 2);
        pane.add(new Label("Cy:"), 0, 3);
        TextField cy = new TextField();
        pane.add(cy, 1, 3);
        pane.add(new Label("Nom:"), 0, 4);
        TextField nomp = new TextField();
        pane.add(nomp, 1, 4);
        
        pane.add(new Label("Surface Pièce:"), 0, 5);
        Label surface = new Label("--");
        pane.add(surface,1,5);

        ArrayList<Piece> liste_Pieces = new ArrayList<Piece>();

        // Bouton permettant d'ajouter un rectangle à la liste liste_recs et de calculer sa surface.
        Button btAdd = new Button("Ajouter pièce");
        pane.add(btAdd, 0, 6);
        // Expression lambda pour construire un EventHandler<ActionEvent>
        btAdd.setOnAction(evt -> {
            Piece P = new Piece( Double.parseDouble(lon.getText()), 
                                 Double.parseDouble(lar.getText()), 
                                 Double.parseDouble(cx.getText()),
                                 Double.parseDouble(cy.getText())
                                 String.String(nomp_piece.getText()));
            liste_Pieces.add(P);
            // Calcul et affichage de la surface
            surface.setText(Double.toString(P.surface()));
            System.out.println("Piece : " + lon.getText() + " x " + lar.getText() + 
                               " (" + cx.getText() + "," + cy.getText()+") ajouté à la liste");
        });

        Button btSave = new Button("Sauvegarder pieces");
        pane.add(btSave, 1, 6);
        btSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evt) {
                PrintWriter pw;
                try {
                    pw = new PrintWriter(new FileOutputStream("Pieces.txt"));
                    for (Piece P : liste_Pieces)
                        pw.println("Pièce;"+P.getLon()+";"+P.getLar()+";"+P.getOri_x()+";"+P.getOri_y()+";"+P.surface());
                    pw.close();
                    System.out.println("Pièce ajoutée à la liste");
                } catch (FileNotFoundException e) {            
                    e.printStackTrace();
                }
            }
        });
        
        Pane paneH = new Pane();

        Button btShow = new Button("Dessiner pièces");
        pane.add(btShow, 2, 6);
        btShow.setOnAction(evt -> {
            for (int i=0;i<liste_Pieces.size();i++) {
                Text text = new Text(nomp.getText());
                StackPane stack = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setX(liste_Pieces.get(i).getOri_x());
                rectangle.setY(liste_Pieces.get(i).getOri_y());
                rectangle.setWidth(liste_Pieces.get(i).getLar());
                rectangle.setHeight(liste_Pieces.get(i).getLon());
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.WHITE); 
                
                stack.setAlignment(Pos.CENTER);
                stack.getChildren().addAll(rectangle, text);
                stack.setLayoutX(liste_Pieces.get(i).getOri_x());
                stack.setLayoutY(liste_Pieces.get(i).getOri_y());
                
                paneH.getChildren().addAll(stack);
            } 
        });

        VBox paneV = new VBox();
        paneV.setPadding(new Insets(10, 50, 50, 50));
        paneV.setSpacing(10);
        
        paneV.getChildren().add(pane);
        paneV.getChildren().add(paneH);
        
        // Graphe de scène avec des nœuds
        Scene scene = new Scene(paneV, 600, 500);   // Construire une scène à partir de la racine du graphe de scène
        primaryStage.setScene(scene);               // The stage sets scene
        primaryStage.setTitle("Pièces");        // Définir le titre de la fenêtre
        primaryStage.show();                        // Définir la visibilité (l'afficher)
    }

    public static void main(String[] args) {
        launch(args);
    }
}