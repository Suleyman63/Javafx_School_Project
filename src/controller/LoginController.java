package controller;


import db.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    private Connection con = DBConnect.getInstance().connect();

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private Button cancel;

    @FXML
    private Label invalidLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    void cancelBtn(ActionEvent event) {

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    @FXML
    void loginBtn(ActionEvent event) throws IOException {

        if (usernameText.getText().isBlank() || passwordText.getText().isBlank()) {
            invalidLabel.setStyle(errorMessage);

            if (usernameText.getText().isBlank() || passwordText.getText().isBlank()) {
                invalidLabel.setText("login fields are required!");
                usernameText.setStyle(errorStyle);
                passwordText.setStyle(errorStyle);
            } else
                if (usernameText.getText().isBlank()) {
                    usernameText.setStyle(errorStyle);
                    invalidLabel.setText("username is required!");
                    passwordText.setStyle(successStyle);

                } else
                    if (passwordText.getText().isBlank()) {
                        passwordText.setStyle(errorStyle);
                        invalidLabel.setText("password is required!");
                        usernameText.setStyle(successStyle);
                    }
        } else
            if (passwordText.getText().length() < 4) {
                invalidLabel.setText("password can't be less than 4 characters!");
                invalidLabel.setStyle(errorMessage);
                passwordText.setStyle(errorStyle);
            } else {
                validateLogin();
                invalidLabel.setText("Login Successful!");
                invalidLabel.setStyle(successMessage);
                usernameText.setStyle(successStyle);
                passwordText.setStyle(successStyle);

                Main m = new Main();
                m.changeScene("/ui/dashbord.fxml");
            }
    }


    public void validateLogin(){
        String verifyLogin = "SELECT * FROM admin WHERE username = '" +
                usernameText.getText() + "' AND password ='" + passwordText.getText()+ "'";

        try {
            Statement statment = con.createStatement();
            ResultSet query = statment.executeQuery(verifyLogin);
            while (query.next()){
                if (query.getInt(2)==2){
                    invalidLabel.setText("login successfully");
                }else {
                    invalidLabel.setText("invalid login. please try again");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
