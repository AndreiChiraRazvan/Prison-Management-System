package com.example.penitenciarv1;


import java.io.IOException;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Entities.User;
import com.example.penitenciarv1.Interfaces.InterfataDetinut;
import com.example.penitenciarv1.Interfaces.InterfataGardian;
import com.example.penitenciarv1.Interfaces.InterfataVizitator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            if(newUser.getAccessRights() == 0){
                ;
            }
            if (newUser.getAccessRights() == 1)
            {

                InterfataGardian newInterfataGardian = new InterfataGardian();
                newInterfataGardian.start(newStage);
            }
            if (newUser.getAccessRights() == 2)
            {
                InterfataDetinut newInterfataDetinut = new InterfataDetinut();
                newInterfataDetinut.start(newStage);
            };
            if (newUser.getAccessRights() == 3){
                InterfataVizitator newInterfataVizitator = new InterfataVizitator();
                newInterfataVizitator.start(newStage);
            }

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

            Stage failedLogin = (Stage) usr_txt.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("failed_login.fxml"));
            failedLogin.setScene(new Scene(root));
            failedLogin.setTitle("Failed_Login");
            failedLogin.centerOnScreen();

            System.out.println("Wrong username or password");
        }
    }



    @FXML
    public void failed_login_btn_onClick(ActionEvent e) throws IOException {
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Stage returnToLogin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(("hello-view.fxml")));
        returnToLogin.setScene(new Scene(root));
        returnToLogin.show();
        stage.close();
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