package com.example.penitenciarv1.Interfaces.popUps;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Listeners.DynamicScalingAppIntGardianDetinut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddToNewCellController implements Initializable {

    private String inmateId;
    private int guardianId;

    public AddToNewCellController() {

    }

    public void setInmateId(String inmateId) {
        this.inmateId = inmateId;
    }

    public void setGuardianId(int guardianId) {
        this.guardianId = guardianId;
    }

    @FXML
    private Button button;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Pane mainPane;

    DatabaseConnector databaseConnector = new DatabaseConnector();

    @FXML
    void updateAndExit(ActionEvent event) {
        String comboBoxSelection = comboBox.getValue();

        if (comboBoxSelection != null) {
            int selectedCellId = Integer.parseInt(comboBoxSelection);

            databaseConnector.updateInmateCell(Integer.parseInt(inmateId), selectedCellId);
            Stage newStage = new Stage();
            openMainScene(newStage);
        } else {
            NoActionPopUp noActionPopUp = new NoActionPopUp();
            Stage newStageErr = new Stage();
            Stage newStage = new Stage();
            openMainScene(newStage);
            try {
                noActionPopUp.start(newStageErr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void openMainScene(Stage newStage) {
        DynamicScalingAppIntGardianDetinut newIntGardian = new DynamicScalingAppIntGardianDetinut(guardianId);
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        newIntGardian.start(newStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(inmateId + " controller");
        ArrayList<String> emptyCells = databaseConnector.getEmptyCells();
        comboBox.getItems().addAll(emptyCells);
    }
}
