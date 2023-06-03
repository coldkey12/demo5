package com.example.demo5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.CurrentUser;

public class LoggedIn {
    @FXML
    private Button addTask;

    @FXML
    private Button logOut;

    @FXML
    private Button progress;

    @FXML
    private Button tasks;

    @FXML
    private Label user_credentials;
    @FXML
    void initialize() {
        user_credentials.setText(String.format("Welcome back! %s %s", CurrentUser.firstName,CurrentUser.secondName));
        logOut.setOnAction(actionEvent -> {
            try {
                logOut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoggedIn.class.getResource("hello-view.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                CurrentUser.userName=null;
                CurrentUser.firstName=null;
                CurrentUser.secondName=null;
                CurrentUser.user_id=0;
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
        addTask.setOnAction(actionEvent -> {
            try {
                logOut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoggedIn.class.getResource("addTask.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        tasks.setOnAction(actionEvent -> {
            try{
                logOut.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoggedIn.class.getResource("MyTasks.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception exception){
                System.out.println(exception.getCause());
            }
        });
    }

}
