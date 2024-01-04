package com.example.sidereader;

public class Courses {

    private int id;
    private String name;
    private String courseURL;


    public Courses(int id, String name, String courseURL) {
        this.id = id;
        this.name = name;
        this.courseURL = courseURL;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseURL='" + courseURL + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseURL() {
        return courseURL;
    }

    public void setCourseURL(String courseURL) {
        this.courseURL = courseURL;
    }

}
