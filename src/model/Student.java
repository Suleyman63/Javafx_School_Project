package model;

import java.time.LocalDate;
import java.util.Objects;

public class Student extends School{

    private LocalDate dateofbirth;
    private String klasse;


    public Student(int id, String firstname, String lastname, int phone, String email, LocalDate dateofbirth, String klasse) {
        super(id, firstname, lastname, phone, email);
        this.dateofbirth = dateofbirth;
        this.klasse = klasse;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return dateofbirth.equals(student.dateofbirth) && klasse.equals(student.klasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateofbirth, klasse);
    }

    @Override
    public String toString() {
        return "Student{" +
                "dateofbirth=" + dateofbirth +
                ", klasse='" + klasse + '\'' +
                '}';
    }
}
