package com.radoll.serep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("serep.fxml"));


        Scene scene = new Scene(root, 600, 600);






        stage.setTitle("Serep Prototype");
        stage.setScene(scene);
        stage.show();
    }

}
