package model;

import java.util.Objects;

public abstract class School {

    private int id;
    private String firstname;
    private String lastname;
    private int phone;
    private String email;

    public School(int id, String firstname, String lastname, int phone, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && phone == school.phone && firstname.equals(school.firstname) && lastname.equals(school.lastname) && email.equals(school.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, phone, email);
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}

