package com.technawabs.sandesh;

public class Sms {

    public long id;
    public String address;
    public String message;
    public String date;
    public SmsContact smsSender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SmsContact getSmsSender() {
        return smsSender;
    }

    public void setSmsSender(SmsContact smsSender) {
        this.smsSender = smsSender;
    }
}
