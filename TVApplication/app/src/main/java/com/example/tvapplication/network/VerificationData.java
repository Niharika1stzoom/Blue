package com.example.tvapplication.network;

public class VerificationData {
    String device_code,user_code,verification_url;
    int expires_in, interval;

    public String getDevice_code() {
        return device_code;
    }

    public String getUser_code() {
        return user_code;
    }

    public String getVerification_url() {
        return verification_url;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public int getInterval() {
        return interval;
    }
}


