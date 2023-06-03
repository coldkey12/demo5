package com.example.demo5;

import animations.Shaker;
import db.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignUpController {
    @FXML
    private Button backToLogin;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Label choiceboxAlert;

    @FXML
    private TextField first_name;

    @FXML
    private Label firstnameAlert;

    @FXML
    private Label passwordAlert;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField second_name;

    @FXML
    private Label secondnameAlert;

    @FXML
    private Button signUp;

    @FXML
    private Label usernameAlert;

    @FXML
    private TextField usernameField;
    @FXML
    void initialize(){
        ObservableList<String> langs = FXCollections.observableArrayList("Male","Female");
        choiceBox.setItems(langs);
        DBHandler dbHandler = new DBHandler();
        Shaker usernameShaker = new Shaker(usernameField);
        Shaker choiceboxShaker = new Shaker(choiceBox);
        Shaker firstnameShaker = new Shaker(first_name);
        Shaker secondnameShaker = new Shaker(second_name);
        Shaker passwordShaker = new Shaker(passwordField);
        signUp.setOnAction(actionEvent -> {
            boolean flagUsername = false;
            boolean flagPassword = false;
            boolean flagFirstname = false;
            boolean flagSecondname = false;
            int gender = 0;
            if(choiceBox.getValue() == null){
                choiceboxAlert.setText("We don't support non-binary");
            } else if(choiceBox.getValue().equals("Male")){
                gender = 1;
            } else {
                gender = 0;
            }
            if(!usernameField.getText().equals("")){// ----------------------------IF USERNAME EMPTY OP
                try {
                    if(dbHandler.checkExistance(usernameField.getText())){// ------------------IF USERNAME TAKEN OP
                        usernameField.setText("");
                        usernameShaker.shake();
                        usernameAlert.setText("This username is taken already");
                    } else {
                        flagUsername=true;
                    }// -----------------------------------------------------------------------IF USERNAME TAKEN CL
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                usernameField.setText("");
                usernameShaker.shake();
                usernameAlert.setText("Username field can't be empty!");
            }// --------------------------------------------------------------------IF USERNAME EMPTY CL
            if(passwordField.getText().equals("")){// -----------------------------IF PASSWORD EMPTY OP
                passwordField.setText("");
                passwordShaker.shake();
                passwordAlert.setText("Password field can't be empty!");
            } else {
                flagPassword=true;
            }//---------------------------------------------------------------------IF PASSWORD EMPTY CL
            if(first_name.getText().equals("")){//---------------------------------IF FIRSTNAME EMPTY OP
                first_name.setText("");
                firstnameShaker.shake();
                firstnameAlert.setText("First name field can't be empty!");
            } else{
                flagFirstname=true;
            }
            if(second_name.getText().equals("")){
                second_name.setText("");
                secondnameShaker.shake();
                secondnameAlert.setText("You don't have a second name?");
            } else {
                flagSecondname=true;
            }
            if(flagFirstname && flagPassword && flagUsername && flagSecondname){
                try {
                    dbHandler.registration(usernameField.getText(),passwordField.getText(),first_name.getText(),
                            second_name.getText(),gender);
                    signUp.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SignUpController.class.getResource("hello-view.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        backToLogin.setOnAction(actionEvent -> {
            try {
                signUp.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SignUpController.class.getResource("hello-view.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
    }
}
