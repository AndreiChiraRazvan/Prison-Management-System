package com.example.penitenciarv1.Interfaces;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Entities.User;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WardenVizitator extends InterfataVizitator{
    public WardenVizitator(DatabaseConnector databaseConnector, Stage stage2, User newUser) {
        super(stage2, databaseConnector, newUser);

    }
    public WardenVizitator(){
        super();
    }


}
