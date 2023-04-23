package com.example.bookticketsfirebase.model;

public class CarSeat {
    private String carSeat;
    private String payment;

    public CarSeat(String carSeat, String payment) {
        this.carSeat = carSeat;
        this.payment = payment;
    }

    public String getCarSeat() {
        return carSeat;
    }

    public void setCarSeat(String carSeat) {
        this.carSeat = carSeat;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
