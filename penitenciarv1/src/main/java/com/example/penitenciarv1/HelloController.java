package com.example.penitenciarv1;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    private String secretPw1 = "sUp3RsTroNGp4ssW0rd";
    private String username1 = "user";

    @FXML
    private TextField usr_txt;
    @FXML
    private PasswordField pw_txt;

    @FXML
    public void login_btn_onClick(ActionEvent e) throws IOException {
        String username = usr_txt.getText();
        String password = pw_txt.getText();
        System.out.println(username + password);
        if(username.equals(username1) && password.equals(secretPw1)) {
            System.out.println("Succesful login");
            Stage stage = (Stage) usr_txt.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("interfatagardian.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.setTitle("Sapply - Administrator");
        }else {
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
        Stage returnToLogin = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(("hello-view.fxml")));
        returnToLogin.setScene(new Scene(root));
        returnToLogin.show();
    }

    @FXML
    public void onEnter(ActionEvent ae){
        try {
            login_btn_onClick(ae);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem when introducing your data!");
        }
    }
}