package com.example.demo5;

import db.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.CurrentUser;

import java.time.format.DateTimeFormatterBuilder;

public class AddTask {
    @FXML
    private Button addTask;

    @FXML
    private Button backToMenu;

    @FXML
    private TextField taskDesc;

    @FXML
    private TextField taskName;
    @FXML
    void initialize(){
        backToMenu.setOnAction(actionEvent -> {
            try {
                backToMenu.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AddTask.class.getResource("loggedIn.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        addTask.setOnAction(actionEvent -> {
            try {
                DBHandler dbHandler = new DBHandler();
                dbHandler.addTask(taskName.getText(),taskDesc.getText(), CurrentUser.user_id);
                taskName.setText("");
                taskDesc.setText("");
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
    }
}
