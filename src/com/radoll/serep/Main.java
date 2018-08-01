package com.radoll.serep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serep.fxml"));


        Scene scene = new Scene(root, 600, 350);


        stage.setTitle("Serep Prototype");
        stage.setScene(scene);
        stage.show();
    }

}
