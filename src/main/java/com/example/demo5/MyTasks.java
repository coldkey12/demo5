package com.example.demo5;
import db.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;

public class MyTasks {
    @FXML
    private Button backToMenu;
    @FXML
    private ListView<String> listTasks;
    @FXML
    private TextField task_desc_field;
    @FXML
    private TextField task_status_field;
    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.myTasks();
        try{
            while (resultSet.next()){
                listTasks.getItems().add(resultSet.getString(1));
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        backToMenu.setOnAction(actionEvent -> {
            try {
                backToMenu.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedIn.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listTasks.setOnMouseClicked(actionEvent -> {
            try {
                ResultSet resultSet1 = dbHandler.returnByTaskName(listTasks.getSelectionModel().getSelectedItem());
                resultSet1.next();
                task_desc_field.setText(resultSet1.getString(1));
                task_status_field.setText(resultSet1.getString(2));
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
    }
}