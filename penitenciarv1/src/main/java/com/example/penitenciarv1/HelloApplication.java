package com.example.penitenciarv1;

import com.example.penitenciarv1.Database.DatabaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Use an absolute path to load the FXML file
//            String fxmlPath = "./hello-view.fxml";
//            File fxmlFile = new File(fxmlPath);
//            URL fxmlUrl = fxmlFile.toURI().toURL();
            DatabaseConnector databaseConnector = new DatabaseConnector();
            databaseConnector.callRandomProcedure();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            primaryStage.setTitle("Login");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file. Ensure the file path is correct and the file exists.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}