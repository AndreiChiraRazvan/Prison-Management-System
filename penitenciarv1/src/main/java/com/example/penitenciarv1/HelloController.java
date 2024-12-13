package com.example.penitenciarv1;


import java.io.IOException;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.penitenciarv1.Interfatagardian.changeBackground;

public class HelloController {


    private DatabaseConnector databaseConnector = new DatabaseConnector();
    @FXML
    private TextField usr_txt;
    @FXML
    private PasswordField pw_txt;

    @FXML
    public void login_btn_onClick(ActionEvent e) throws IOException {
        String username = usr_txt.getText();
        String password = pw_txt.getText();
        System.out.println(username + password);
        User newUser = databaseConnector.checkAndReturnUser(username, password);
        if (newUser != null) {
//            System.out.println("Succesful login");
//            Stage stage = (Stage) usr_txt.getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource("interfatagardian.fxml"));
//            stage.setScene(new Scene(root));
//            stage.centerOnScreen();
//            stage.setTitle("Sapply - Administrator");
            //Parent root = FXMLLoader.load(getClass().getResource("interfatagardian.fxml"));
//
            // Scene scene2 = new Scene(root, 600, 450);


            //scene2.getStylesheets().addAll(this.getClass().getResource("demo.css").toExternalForm());
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            stage.close();

            Stage newStage = new Stage();
            Interfatagardian newInterfatagardian = new Interfatagardian();
            newInterfatagardian.start(newStage);
//
//            /// /////////////aici se face un if sau case in functie de shift apelam pentru alta imagine
            //Interfatagardian.changeBackground(scene2, "blue");

//            System.out.println("inainte");
//            Stage newStage = new Stage();
           // newStage.setScene(scene2);
            //pentru setare minim si maxim
//            primaryStage.setMinWidth(500);
//            primaryStage.setMinHeight(500);

           // newStage.show();
        } else {
            System.out.println("Wrong username or password");
        }
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        try {
            login_btn_onClick(ae);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem when introducing your data!");
        }
    }
}