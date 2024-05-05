package com.example.mad_cw.course;

import java.io.Serializable;

public class CourseRegisterModel implements Serializable {

    private int id;
    private String course_name;
    private String course_branch;
    private String name;
    private String nic;
    private String email;
    private String telephone;
    private String address;
    private String promotionCode;
    private double discountAmount;
    private double finalTotal;
    private double courseAmount;

    public CourseRegisterModel(int id,String course_name,String course_branch, String name, String email, String telephone, String address, String nic, String promotionCode,double discountAmount, double finalTotal, double courseAmount) {
        this.id = id;
        this.course_name = course_name;
        this.course_branch = course_branch;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.nic = nic;
        this.promotionCode = promotionCode;
        this.courseAmount = courseAmount;
        this.discountAmount = discountAmount;
        this.finalTotal = finalTotal;
    }

    @Override
    public String toString() {
        return "CourseRegisterModel{" +
                "id=" + id +
                "course_name=" + course_name +
                "course_branch=" + course_branch +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", promotionCode='" + promotionCode + '\'' +
                ", courseAmount=" + courseAmount +
                ", discountAmount=" + discountAmount +
                ", finalTotal=" + finalTotal +
                '}';
    }

    public CourseRegisterModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_branch() {
        return course_branch;
    }

    public void setCourse_branch(String course_branch) {
        this.course_branch = course_branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public double getCourseAmount() {
        return courseAmount;
    }

    public void setCourseAmount(double courseAmount) {
        this.courseAmount = courseAmount;
    }
}
