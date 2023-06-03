package com.example.demo5;

import animations.Shaker;
import db.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objects.CurrentUser;
import objects.User;

import java.sql.ResultSet;

public class LoginController {
    @FXML
    private Label alertLabel;
    @FXML
    private Label welcomeText;
    @FXML
    private Button nextButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void initialize() {
        nextButton.setOnAction(actionEvent -> {
            try {
                nextButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(LoginController.class.getResource("signup.fxml"));
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        loginButton.setOnAction(actionEvent -> {
            try {
                DBHandler dbHandler = new DBHandler();
                if(dbHandler.login(usernameField.getText(),passwordField.getText())){
                    User user = dbHandler.getUser(usernameField.getText(),passwordField.getText());
                    if(user!=null){
                        ResultSet resultSet;
                        CurrentUser.firstName = user.getFirst_name();
                        CurrentUser.secondName = user.getSecond_name();
                        resultSet = dbHandler.idByUsername(usernameField.getText());
                        resultSet.next();
                        CurrentUser.user_id = resultSet.getInt(1);
                    }
                    loginButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(LoginController.class.getResource("loggedIn.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    alertLabel.setText("Invalid password or username");
                    Shaker shakerUsername = new Shaker(usernameField);
                    Shaker shakerPassword = new Shaker(passwordField);
                    shakerUsername.shake();
                    shakerPassword.shake();
                    usernameField.setText("");
                    passwordField.setText("");
                };
            } catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
    }
}
