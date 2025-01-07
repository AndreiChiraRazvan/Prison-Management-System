package com.example.penitenciarv1.Interfaces.popUps.solitudeRoom;

import com.example.penitenciarv1.Database.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddToSolitudeController {

    private String inmateID;
    private int guardianUserID;

    public void setInmateID(String inmateID) {
        this.inmateID = inmateID;
    }

    public void setGuardianUserID(int guardianUserID) {
        this.guardianUserID = guardianUserID;
    }
    DatabaseConnector dbConnector = new DatabaseConnector();


    @FXML
    private TextField enterFinalTimeField;

    @FXML
    void updateAndExit(ActionEvent event) {

    }

   @FXML
   public void initialize() {

   }


}
