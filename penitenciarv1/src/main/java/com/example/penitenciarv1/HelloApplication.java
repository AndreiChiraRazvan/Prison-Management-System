package com.example.penitenciarv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

            Parent root = fxmlLoader.load();

            primaryStage.setTitle("LogIn");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            StackPane root2 = new StackPane();
            root.setId("pane");


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file. Ensure the file path is correct and the file exists.");
        }
    }
    private void changeBackground(Scene scene, String color) {
        scene.getRoot().setStyle(String.format(
                "-fx-background-color: %s;" +
                        "-fx-background-position: center; " +
                        "-fx-background-repeat: no-repeat;",
                color));
    }

    public static void main(String[] args) {
        launch(args);
    }
}