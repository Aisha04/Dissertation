package com.example.train;

public class MainClientdb {
    public String id, fullName, Username, Email, Gender, Client;

    public MainClientdb() {
    }

    public MainClientdb(String id, String fullName, String username, String email, String gender, String client) {
        this.id = id;
        this.fullName = fullName;
        Username = username;
        Email = email;
        Gender = gender;
        Client = client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }
}
