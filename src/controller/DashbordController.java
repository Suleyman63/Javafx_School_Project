package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class DashbordController {

    @FXML
    private Button studentDshboard;

    @FXML
    private Button teacherDsbrd;

    @FXML
    void studentDsBtn(ActionEvent event) throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ui/student.fxml"));
        primaryStage.setTitle("Student Table");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

    }

    @FXML
    void teacherDsBtn(ActionEvent event) throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ui/teacher.fxml"));
        primaryStage.setTitle("Teacher Table");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();

    }

}
