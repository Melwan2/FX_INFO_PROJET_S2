/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_fx_info_s2;
        
        
        
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private List<Coin> coins = new ArrayList<>();
    private List<Mur> murs = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;

    private Coin selectedCoinA = null;
    private Coin selectedCoinB = null;
    private int murId = 1;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Coin and Wall Application");

        // Bouton pour ajouter un coin
        Button addCoinButton = new Button("Ajouter Coin");
        addCoinButton.setOnAction(e -> addCoin());

        // Bouton pour sauvegarder les coins
        Button saveCoinsButton = new Button("Sauvegarder Coins");
        saveCoinsButton.setOnAction(e -> saveCoins());

        // Bouton pour sauvegarder les murs
        Button saveMursButton = new Button("Sauvegarder Murs");
        saveMursButton.setOnAction(e -> saveMurs());

        // Bouton pour calculer la surface d'une pièce
        Button calculateSurfacePieceButton = new Button("Calculer Surface Pièce");
        calculateSurfacePieceButton.setOnAction(e -> calculateAndShowPieceSurface());

        // Canvas pour dessiner les coins et les murs
        canvas = new Canvas(400, 400);
        gc = canvas.getGraphicsContext2D();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);

        // ScrollPane pour contenir le Canvas
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(canvas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // GridPane pour organiser les éléments de l'interface utilisateur
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Button("Ajouter Coin:"), 0, 0);
        gridPane.add(addCoinButton, 0, 1);

        gridPane.add(new Button("Sauvegarder Coins:"), 0, 2);
        gridPane.add(saveCoinsButton, 0, 3);

        gridPane.add(new Button("Sauvegarder Murs:"), 0, 4);
        gridPane.add(saveMursButton, 0, 5);

        gridPane.add(new Button("Calculer Surface Pièce:"), 0, 6);
        gridPane.add(calculateSurfacePieceButton, 0, 7);

        gridPane.add(scrollPane, 0, 8, 1, 1);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);

        // Scène
        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Redimensionnement du canvas lorsque la fenêtre est redimensionnée
        canvas.widthProperty().bind(scrollPane.widthProperty());
        canvas.heightProperty().bind(scrollPane.heightProperty());
    }

    private void addCoin() {
        double x = Math.random() * canvas.getWidth();
        double y = Math.random() * canvas.getHeight();
        Coin coin = new Coin(coins.size() + 1, x, y);
        coins.add(coin);
        draw();
    }

    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        Coin clickedCoin = findCoinAt(x, y);

        if (clickedCoin != null) {
            if (selectedCoinA == null) {
                selectedCoinA = clickedCoin;
            } else if (selectedCoinB == null) {
                selectedCoinB = clickedCoin;
                createMur(selectedCoinA, selectedCoinB);
                selectedCoinA = null;
                selectedCoinB = null;
            }
        }
    }

    private Coin findCoinAt(double x, double y) {
        for (Coin coin : coins) {
            if (Math.abs(coin.getX() - x) < 5 && Math.abs(coin.getY() - y) < 5) {
                return coin;
            }
        }
        return null;
    }

    private void createMur(Coin coinA, Coin coinB) {
        if (coinA.equals(coinB)) {
            showAlert("Erreur", "Un mur ne peut pas avoir le même coin de départ et d'arrivée.");
            return;
        }
        Mur mur = new Mur(murId++, coinA, coinB);
        murs.add(mur);
        draw();
    }

    private void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Coin coin : coins) {
            gc.fillOval(coin.getX() - 3, coin.getY() - 3, 6, 6);
            gc.fillText("C" + coin.getId(), coin.getX() + 5, coin.getY() - 5);
        }
        for (Mur mur : murs) {
            gc.strokeLine(mur.getCoinA().getX(), mur.getCoinA().getY(), mur.getCoinB().getX(), mur.getCoinB().getY());
            gc.fillText("M" + mur.getId(), (mur.getCoinA().getX() + mur.getCoinB().getX()) / 2,
                    (mur.getCoinA().getY() + mur.getCoinB().getY()) / 2 - 5);
        }
    }

    private void saveCoins() {
        try (Buffer edWriter writer = new BufferedWriter(new FileWriter("coins.txt"))) {
            for (Coin coin : coins) {
                    
                writer.write(coin.toString());
                writer.newLine();
            }
            showAlert("Succès", "Les coins ont été sauvegardés dans coins.txt");
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors de la sauvegarde des coins.");
        }
        }

   
    

    private void calculateAndShowPieceSurface() {
        double longestLength = 0;
        double shortestLength = Double.MAX_VALUE;

        for (Mur mur : murs) {
            double length = mur.getLength();
            if (length > longestLength) {
                longestLength = length;
            }
            if (length < shortestLength) {
                shortestLength = length;
            }
        }

        if (longestLength == 0 || shortestLength == Double.MAX_VALUE) {
            showAlert("Erreur", "Aucun mur n'a été créé.");
            return;
        }

        double surface = longestLength * shortestLength;
        showAlert("Surface Pièce", "La surface de la pièce est : " + surface + " unités carrées.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
