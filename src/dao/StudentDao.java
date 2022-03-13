package dao;

import java.time.LocalDate;

import java.util.List;
import model.Student;

public interface StudentDao {

    List<Student> findAllStudent();

    boolean addStudent(Student student);

    boolean deleteStudent(int id);


    boolean updateStudent(int id, String firstname, String lastname, int phone, String email, LocalDate dateofbirth, String klasse);

    List<Student> searchStudent(String firstname);


    void studentExportToCsv();
}
