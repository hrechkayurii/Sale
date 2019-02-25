package com.ua.yuriihrechka.sale.Model;

public class AdminOrders {

    private String address, name, state, city, date, phone, time, totalAmount;


    public AdminOrders() {
    }

    public AdminOrders(String address, String name, String state, String city, String date, String phone, String time, String totalAmount) {
        this.address = address;
        this.name = name;
        this.state = state;
        this.city = city;
        this.date = date;
        this.phone = phone;
        this.time = time;
        this.totalAmount = totalAmount;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
