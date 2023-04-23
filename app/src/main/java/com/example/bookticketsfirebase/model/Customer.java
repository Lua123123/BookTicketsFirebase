package com.example.bookticketsfirebase.model;

public class Customer {
    private String name;
    private String phone;
    private String address;
    private String nameBook;
    private String carSeat;
    private String payment;

    public Customer(String name, String phone, String address, String nameBook, String carSeat, String payment) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nameBook = nameBook;
        this.carSeat = carSeat;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
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
