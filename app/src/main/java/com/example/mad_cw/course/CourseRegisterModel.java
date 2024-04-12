package com.example.mad_cw.course;

import java.io.Serializable;

public class CourseRegisterModel implements Serializable {

    private int id;
    private String name;
    private String nic;
    private String email;
    private String telephone;
    private String address;
    private String promotionCode;

    public CourseRegisterModel(int id, String name, String email, String telephone, String address, String nic, String promotionCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.nic = nic;
        this.promotionCode = promotionCode;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", promotionCode='" + promotionCode + '\'' +
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
}
