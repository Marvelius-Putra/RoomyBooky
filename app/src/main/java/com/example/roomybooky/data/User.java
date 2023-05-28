package com.example.roomybooky.data;

public class User {
    String birthdate;
    String email;
    String name;
    String nim;
    String role;

    public User() {

    }

    public User(String birthdate, String email, String name, String nim, String role) {
        this.birthdate = birthdate;
        this.email = email;
        this.name = name;
        this.nim = nim;
        this.role = role;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
