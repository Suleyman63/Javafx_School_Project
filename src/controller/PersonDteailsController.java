package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonDteailsController implements Initializable {


    @FXML
    private Label dateofbirthLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label klasseLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label phoneLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getDetails();

    }


    public void getDetails(){



    }
}
