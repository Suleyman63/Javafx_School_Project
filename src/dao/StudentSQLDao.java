package dao;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DBConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Student;

import java.util.Optional;

public class StudentSQLDao implements StudentDao {

    private Connection con = DBConnect.getInstance().connect();

    @Override
    public List<Student> findAllStudent() {

        ArrayList<Student> studentList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student ORDER BY id");
            ResultSet rs =  ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                LocalDate dateofbirth = rs.getDate("dateofbirth").toLocalDate();
                String klasse = rs.getString("klasse");
                studentList.add(new Student(id, firstname, lastname, phone, email, dateofbirth, klasse));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public boolean addStudent(Student student) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO student (id,firstname,lastname,phone,email,dateofbirth,klasse)\n" +
                " values (?,?,?,?,?,?,?)")) {
            ps.setInt(1, student.getId());
            ps.setString(2, student.getFirstname());
            ps.setString(3, student.getLastname());
            ps.setInt(4, student.getPhone());
            ps.setString(5,student.getEmail());
            ps.setDate(6, Date.valueOf(student.getDateofbirth()));
            ps.setString(7,student.getKlasse());

            Alert alert  = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Student has been created...");
            alert.showAndWait();

            return ps.executeUpdate()==1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteStudent(int id) {
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id =?")){
                ps.setInt(1, id);
                return ps.executeUpdate()==1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateStudent(int id, String firstname, String lastname, int phone, String email, LocalDate dateofbirth, String klasse) {
        Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to updated?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE student SET firstname=?, lastname=?, phone=?,\n" +
                    " email=?,  dateofbirth=?, klasse =? WHERE id=?")) {
                ps.setString(1,firstname);
                ps.setString(2,lastname);
                ps.setInt(3,phone);
                ps.setString(4,email);
                ps.setDate(5,Date.valueOf(dateofbirth));
                ps.setString(6,klasse);
                ps.setInt(7,id);
                return ps.executeUpdate() == 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    @Override
    public List<Student> searchStudent(String state) {
        try(PreparedStatement ps =con.prepareStatement("SELECT * FROM student WHERE UPPER (firstname) LIKE ?")){
            ps.setString(1, "%" + state.toUpperCase() + "%");
            ResultSet rs = ps.executeQuery();
            return studentResultList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Student> studentResultList(ResultSet rs) throws SQLException {
        ArrayList<Student> list = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            int phone = rs.getInt("phone");
            String email = rs.getString("email");
            LocalDate dateofbirth = rs.getDate("dateofbirth").toLocalDate();
            String klasse = rs.getString("klasse");
            list.add(new Student(id, firstname, lastname, phone, email, dateofbirth, klasse));
        }
        return list;
    }


    public void studentExportToCsv(){

        try(PrintWriter fw = new PrintWriter("STUDENT_LIST.csv")){

            PreparedStatement ps =con.prepareStatement("SELECT * FROM student ORDER BY id");
            ResultSet rs =  ps.executeQuery();

            fw.append("Id");
            fw.append(',');
            fw.append("Firstname");
            fw.append(',');
            fw.append("Lastname");
            fw.append(',');
            fw.append("Phone");
            fw.append(',');
            fw.append("Email");
            fw.append(',');
            fw.append("Birth Date");
            fw.append(',');
            fw.append("Klasse");
            fw.append('\n');

            while (rs.next()){
                fw.append(rs.getString(1));
                fw.append(",");
                fw.append(rs.getString(2));
                fw.append(",");
                fw.append(rs.getString(3));
                fw.append(",");
                fw.append(rs.getString(4));
                fw.append(",");
                fw.append(rs.getString(5));
                fw.append(",");
                fw.append(rs.getDate(6).toString());
                fw.append(",");
                fw.append(rs.getString(7));
                fw.append("\n");
            }

            fw.flush();
            fw.close();
            con.close();
            System.out.println("writed");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
