package com.example.mad_cw.user;

import java.io.Serializable;

public class UserModel implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String gender;
    private String address;
    private String city;
    private String nic;
    private String dob;

    public UserModel(int id, String name, String email, String password, String telephone, String gender, String address, String city, String nic, String dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.nic = nic;
        this.dob = dob;
    }

    public UserModel(String name, String email, String password, String telephone, String gender, String address, String city, String nic, String dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.nic = nic;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", nic='" + nic + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

    public UserModel() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
