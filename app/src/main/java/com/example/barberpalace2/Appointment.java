package com.example.barberpalace2;

import java.util.Date;

public class Appointment {

    String date;
    int day;
    int month;
    int year;
    String time;
    int slot_num;
    String haircut;
    float price;
    String Barber;
    String Name;
    String nickname;
    String email;
    boolean repeat;
    String repeat_frequency;

    public Appointment(){

    }

    public Appointment(String date, int day, int month, int year, String time, int slot_num, String haircut, float price, String barber, String name, String nickname, String email, boolean repeat, String repeat_frequency) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.slot_num = slot_num;
        this.haircut = haircut;
        this.price = price;
        Barber = barber;
        Name = name;
        this.nickname = nickname;
        this.email = email;
        this.repeat = repeat;
        this.repeat_frequency = repeat_frequency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBarber() {
        return Barber;
    }

    public void setBarber(String barber) {
        Barber = barber;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSlot_num() {
        return slot_num;
    }

    public void setSlot_num(int slot_num) {
        this.slot_num = slot_num;
    }

    public String getHaircut() {
        return haircut;
    }

    public void setHaircut(String haircut) {
        this.haircut = haircut;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public String getRepeat_frequency() {
        return repeat_frequency;
    }

    public void setRepeat_frequency(String repeat_frequency) {
        this.repeat_frequency = repeat_frequency;
    }
}
