package com.example.mad_cw.course;

import java.io.Serializable;

public class PromotionCodeModel implements Serializable {
    private int id;
    private String promoCode;
    private double discountPercentage;

    public PromotionCodeModel(int id, String promoCode, double discountPercentage){
        this.id = id;
        this.promoCode = promoCode;
        this.discountPercentage = discountPercentage;
    }

    public PromotionCodeModel(String promoCode, double discountPercentage){
        this.promoCode = promoCode;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "PromotionCodeModel{" +
                "id=" + id +
                ", promoCode='" + promoCode + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    public PromotionCodeModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
