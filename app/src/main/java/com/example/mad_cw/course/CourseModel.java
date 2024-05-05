package com.example.mad_cw.course;

import java.io.Serializable;

public class CourseModel implements Serializable {
    private int id;
    private String name;
    private String branch;
    private String courseStartDate;
    private String publishDate;
    private String registrationClosingDate;
    private String description;
    private String instructor;
    private double fee;
    private int availableSeats;
    private String duration;

    public CourseModel(int id, String name, String description, String branch, int availableSeats, String registrationClosingDate, String courseStartDate, String duration, String publishDate, double fee, String instructor) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.courseStartDate = courseStartDate;
        this.publishDate = publishDate;
        this.registrationClosingDate = registrationClosingDate;
        this.description = description;
        this.instructor = instructor;
        this.fee = fee;
        this.availableSeats = availableSeats;
        this.duration = duration;
    }

    public CourseModel(String name, String description, String branch, int availableSeats, String registrationClosingDate, String courseStartDate, String duration, String publishDate, double fee, String instructor) {
        this.name = name;
        this.branch = branch;
        this.courseStartDate = courseStartDate;
        this.publishDate = publishDate;
        this.registrationClosingDate = registrationClosingDate;
        this.description = description;
        this.instructor = instructor;
        this.fee = fee;
        this.availableSeats = availableSeats;
        this.duration = duration;
    }

    public CourseModel(long courseId, String name, String description, String branch, int availableSeats, String registrationClosingDate, String courseStartDate, String duration, String publishDate, double fee, String instructor) {
    }

    public CourseModel() {
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", registrationClosingDate='" + registrationClosingDate + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", fee=" + fee +
                ", availableSeats=" + availableSeats +
                ", duration=" + duration +
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    public void setRegistrationClosingDate(String registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public CourseModel getCourse(int courseId) {
        return null;
    }
}
