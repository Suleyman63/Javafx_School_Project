package controller;

import dao.TeacherDao;
import dao.TeacherSQLDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Student;
import model.Teacher;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.collections.FXCollections.observableArrayList;

public class TeacherController implements Initializable {

    private TeacherDao teacherDao;

    @FXML
    private Button alllistBtn;


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Teacher, Integer> colAge;

    @FXML
    private TableColumn<Teacher, String> colEmail;

    @FXML
    private TableColumn<Teacher, String> colFirstname;

    @FXML
    private TableColumn<Teacher, Integer> colId;

    @FXML
    private TableColumn<Teacher, String> colLastname;

    @FXML
    private TableColumn<Teacher, String> colBranch;

    @FXML
    private TableColumn<Teacher, Double> colSalary;

    @FXML
    private TableColumn<Teacher, Integer> colPhone;

    @FXML
    private TableView<Teacher> tableview;

    @FXML
    private TextField textSearch;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfBranch;

    @FXML
    private TextField tfSalary;

    @FXML
    void allListBtnOnAction(ActionEvent event) {
        teacherTableReaload();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (validateEmail()){
            teacherDao.addTeacher(new Teacher(Integer.parseInt(tfId.getText()), tfFirstname.getText(),
                    tfLastname.getText(), Integer.parseInt(tfPhone.getText()), tfEmail.getText(),
                    tfBranch.getText(), Double.parseDouble(tfSalary.getText())));
            teacherTableReaload();
            clear();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        teacherDao.deleteTeacher(Integer.parseInt(tfId.getText()));

        teacherTableReaload();
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
    void btnSearchOnAction(ActionEvent event) {

        String teacher = textSearch.getText();
        List<Teacher> findStudent = teacherDao.searchTeacher(teacher);
        tableview.setItems(observableArrayList(findStudent));

        clear();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        teacherDao.updateTeacher(Integer.parseInt(tfId.getText()), tfFirstname.getText(),
                tfLastname.getText(), Integer.parseInt(tfPhone.getText()),tfEmail.getText(),
                tfBranch.getText(), Double.parseDouble(tfSalary.getText()));

        teacherTableReaload();
        clear();
    }

    @FXML
    void exportTeacherCSVOnAction(ActionEvent event){
        try {
            teacherDao.teacherExportToCsv();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        teacherDao=new TeacherSQLDao();

        setTeacherTable();
        teacherTableReaload();
        setTeacherRecord();

    }


    private void setTeacherTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }


    private void teacherTableReaload() {
        tableview.setItems(observableArrayList(teacherDao.findAllTeacher()));
    }

    private void setTeacherRecord(){

        tableview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Teacher st = tableview.getItems().get(tableview.getSelectionModel().getSelectedIndex());
                tfId.setText(String.valueOf(Integer.parseInt(String.valueOf(st.getId()))));
                tfFirstname.setText(st.getFirstname());
                tfLastname.setText(st.getLastname());
                tfPhone.setText(String.valueOf(Integer.valueOf(st.getPhone())));
                tfEmail.setText(st.getEmail());
                tfBranch.setText(st.getBranch());
                tfSalary.setText(String.valueOf(Double.valueOf(Double.valueOf(st.getSalary()))));
            }
        });
    }

    private void clear(){

        tfId.clear();
        tfFirstname.clear();
        tfLastname.clear();
        tfPhone.clear();
        tfEmail.clear();
        tfBranch.clear();
        tfSalary.clear();
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
