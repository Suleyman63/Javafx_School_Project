package dao;

import model.Teacher;

import java.util.List;

public interface TeacherDao {

    List<Teacher> findAllTeacher();

    boolean addTeacher(Teacher teacher);

    boolean deleteTeacher(int id);


    boolean updateTeacher(int id, String firstname, String lastname, int phone, String email, String branch, double salary);

    List<Teacher> searchTeacher(String firstname);

    void teacherExportToCsv();
}
