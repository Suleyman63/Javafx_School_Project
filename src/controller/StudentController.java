package controller;

import dao.StudentDao;
import dao.StudentSQLDao;
import db.DBConnect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Student;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.DatePicker;

import javax.swing.*;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.fxml.FXMLLoader.load;

public class StudentController implements Initializable {

    private StudentDao studentdao;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnExit;

    @FXML
    private TableView<Student> tableview;

    @FXML
    private TableColumn<Student, Integer> colId;

    @FXML
    private TableColumn<Student, String> colFirstname;

    @FXML
    private TableColumn<Student, String> colLastname;

    @FXML
    private TableColumn<Student, LocalDate> colDateofbirth;

    @FXML
    private TableColumn<Student, Integer> colPhone;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, String> colKlasse;

    @FXML
    private DatePicker tfDateofbirth;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfKlasse;

    @FXML
    private TextField textSearch;

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (validateEmail()){
            studentdao.addStudent(new Student(Integer.parseInt(tfId.getText()), tfFirstname.getText(),
                    tfLastname.getText(), Integer.parseInt(tfPhone.getText()), tfEmail.getText(),
                    tfDateofbirth.getValue(), tfKlasse.getText()));

            tableReaload();
            clear();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        studentdao.deleteStudent(Integer.parseInt(tfId.getText()));
         tableReaload();
         clear();
    }


    @FXML
    void allListBtnOnAction(ActionEvent event) {
        tableReaload();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

        String student = textSearch.getText();
        List<Student> findStudent = studentdao.searchStudent(student);
        tableview.setItems(observableArrayList(findStudent));
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }


    @FXML
    void btnOnExit(ActionEvent event) {
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to exit?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        studentdao.updateStudent(Integer.parseInt(tfId.getText()),
                tfFirstname.getText(),
                tfLastname.getText(),
                Integer.parseInt(tfPhone.getText()),
                tfEmail.getText(),
                tfDateofbirth.getValue(),
                tfKlasse.getText());

        tableReaload();
        clear();
    }


    @FXML
    void exportOnAction(ActionEvent event){
        try {
            studentdao.studentExportToCsv();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentdao=new StudentSQLDao();
        setTable();
        tableReaload();
        setRecord();
    }


    private void setTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDateofbirth.setCellValueFactory(new PropertyValueFactory<>("dateofbirth"));
        colKlasse.setCellValueFactory(new PropertyValueFactory<>("klasse"));

    }

    private void tableReaload() {
        tableview.setItems(observableArrayList(studentdao.findAllStudent()));
    }


    private void setRecord(){
        tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getButton() == MouseButton.PRIMARY) {
                    Student st = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
                    tfId.setText(String.valueOf(Integer.parseInt(String.valueOf(st.getId()))));
                    tfFirstname.setText(st.getFirstname());
                    tfLastname.setText(st.getLastname());
                    tfPhone.setText(String.valueOf(Integer.valueOf(st.getPhone())));
                    tfEmail.setText(st.getEmail());
                    tfDateofbirth.setValue(st.getDateofbirth());
                    tfKlasse.setText(st.getKlasse());
                }else if (event.getButton() == MouseButton.SECONDARY){
                    Stage primaryStage = new Stage();
                    Parent root = null;
                    try {
                        root = load(getClass().getResource("/ui/persondetails.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.setTitle("Student Details");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.initStyle(StageStyle.UTILITY);
                    primaryStage.show();
                }
            }
        });
    }


    private void clear(){
        tfId.clear();
        tfFirstname.clear();
        tfLastname.clear();
        tfPhone.clear();
        tfEmail.clear();
        tfDateofbirth.setValue(LocalDate.now());
        tfKlasse.clear();
        textSearch.clear();
    }


    private boolean validateEmail(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(tfEmail.getText());

        if(m.find() && m.group().equals(tfEmail.getText())){
            return true;
        }else {
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Email validation");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            return false;
        }
    }

}
