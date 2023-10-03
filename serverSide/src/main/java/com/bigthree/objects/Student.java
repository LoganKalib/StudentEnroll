package com.bigthree.objects;

import java.io.Serializable;

public class Student implements Serializable{
    private String name, surname, password;
    private int number;

    public Student(String name, String surname, String password, int number) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.number = number;
    }

    public Student(String password, int number) {
        this.password = password;
        this.number = number;
        this.name = null;
        this.surname = null;
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

    public int getNumber() {
        return number;
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

    public void setNumber(int number) {
        this.number = number;
    }
    
    @Override
    public String toString(){
        return this.name + " " + this.getSurname() + "!";
    }
}
