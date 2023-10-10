package com.bigthree.objects;

import java.io.Serializable;

public class Enrolled implements Serializable{
    private int StudNum;
    private String code;
    private boolean delete = false;

    public int getStudNum() {
        return StudNum;
    }

    public String getCode() {
        return code;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    

    public void setStudNum(int StudNum) {
        this.StudNum = StudNum;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Enrolled(int StudNum, String code) {
        this.StudNum = StudNum;
        this.code = code;
    }
    
    @Override
    public String toString(){
        return StudNum + " " + code;
    }
    
}
