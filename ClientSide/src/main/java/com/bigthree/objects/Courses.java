package com.bigthree.objects;

public class Courses {
    private String code, name;
    private int price;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Courses(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
    
    @Override 
    public String toString(){
        return code + " " + name + 
                " R" + price;
    }
}
