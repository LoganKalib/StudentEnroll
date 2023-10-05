package com.bigthree.objects;

import java.io.Serializable;

/**
 *
 * @author logan
 */
public class NewEnroll implements Serializable{
    private Student stud;
    private Courses course;

    public Student getStud() {
        return stud;
    }

    public Courses getCourse() {
        return course;
    }

    public void setStud(Student stud) {
        this.stud = stud;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public NewEnroll(Student stud, Courses course) {
        this.stud = stud;
        this.course = course;
    }
    
    @Override
    public String toString(){
        return this.course.getCode() + " - " + this.course.getName() + " - " + this.stud.getNumber();
                
    }
}
