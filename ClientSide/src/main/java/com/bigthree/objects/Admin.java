package com.bigthree.objects;

public class Admin {
    private String name, surname, password, username;


    public Admin(String name, String surname, String password, String user) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = user;
    }
    
    public Admin(String password, String user) {
        this.name = null;
        this.surname = null;
        this.password = password;
        this.username = user;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public String toString(){
        return this.name + " " + this.surname + "!";
    }
    
}
