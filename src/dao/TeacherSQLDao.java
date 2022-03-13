package dao;

import db.DBConnect;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Teacher;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TeacherSQLDao implements TeacherDao {

    private Connection con = DBConnect.getInstance().connect();


    @Override
    public List<Teacher> findAllTeacher() {

        ArrayList<Teacher> teacherList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM teacher ORDER BY id");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                String branch = rs.getString("branch");
                double salary = rs.getDouble("salary");
                teacherList.add(new Teacher(id, firstname, lastname, phone, email, branch, salary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO teacher (id,firstname,lastname,phone,email,branch,salary)\n" +
                " values (?,?,?,?,?,?,?)")) {
            ps.setInt(1, teacher.getId());
            ps.setString(2, teacher.getFirstname());
            ps.setString(3, teacher.getLastname());
            ps.setInt(4, teacher.getPhone());
            ps.setString(5, teacher.getEmail());
            ps.setString(6, teacher.getBranch());
            ps.setDouble(7, teacher.getSalary());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Infirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Teacher has been created...");
            alert.showAndWait();

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTeacher(int id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM teacher WHERE id =?")) {
                ps.setInt(1, id);
                return ps.executeUpdate() == 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateTeacher(int id, String firstname, String lastname, int phone, String email, String branch, double salary) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to updated?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try (PreparedStatement ps = con.prepareStatement("UPDATE teacher SET firstname = '" +
                    firstname + "', lastname = '" + lastname + "', email = '" + email + "', branch = '" + branch +"', salary =" +
                    salary + ", phone =" + phone + "WHERE id= " + id + "")) {
                return ps.executeUpdate() == 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Teacher> searchTeacher(String state) {
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM teacher WHERE UPPER(firstname) LIKE ?")) {
            ps.setString(1, "%"+state.toUpperCase()+"%");
            ResultSet rs = ps.executeQuery();
            return resultTeacherList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Teacher> resultTeacherList(ResultSet rs) throws SQLException {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstanme = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            int phone = rs.getInt("phone");
            String email = rs.getString("email");
            String branch = rs.getString("branch");
            double salary = rs.getDouble("salary");
            teacherList.add(new Teacher(id, firstanme, lastname, phone, email, branch, salary));
        }
        return teacherList;
    }


    public void teacherExportToCsv(){

        try(PrintWriter fw = new PrintWriter("TEACHER_LIST.csv")){

            PreparedStatement ps =con.prepareStatement("SELECT * FROM teacher ORDER BY id");
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
            fw.append("Branch");
            fw.append(',');
            fw.append("Salary");
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
                fw.append(rs.getString(6));
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