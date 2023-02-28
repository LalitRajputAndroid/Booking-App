package com.example.makemytrip;

public class Modal {
    private String email;
    private  String pass;
    private String id;
    private String username;

    public Modal() {
    }

    public Modal(String email, String pass, String id, String username) {
        this.email = email;
        this.pass = pass;
        this.id = id;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
