package com.example.penitenciarv1;

import com.example.penitenciarv1.Database.DatabaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {



            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("interfatagardian.fxml"));

            // Use an absolute path to load the FXML file
//            String fxmlPath = "./hello-view.fxml";
//            File fxmlFile = new File(fxmlPath);
//            URL fxmlUrl = fxmlFile.toURI().toURL();
          
            Parent root = fxmlLoader.load();

            primaryStage.setTitle("Gardian-Meniu principal");
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//          StackPane root2 = new StackPane();
            root.setId("pane");
            Scene scene2 = new Scene(root, 600, 450);
            scene2.getStylesheets().addAll(this.getClass().getResource("demo.css").toExternalForm());
            primaryStage.setScene(scene2);
            //pentru setare minim si maxim
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(500);

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