package model;

import java.util.Objects;

public class Teacher extends School{


    private String branch;
    private double salary;

    public Teacher(int id, String firstname, String lastname, int phone, String email, String branch, double salary) {
        super(id, firstname, lastname, phone, email);
        this.branch = branch;
        this.salary = salary;
    }

    public double getGehalt() {

        double gehalt =salary*0.80;

        return gehalt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getSalary() {
        return this.getGehalt();
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Double.compare(teacher.salary, salary) == 0 && Objects.equals(branch, teacher.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), branch, salary);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "branch='" + branch + '\'' +
                ", salaray=" + this.getGehalt() +
                '}';
    }
}
