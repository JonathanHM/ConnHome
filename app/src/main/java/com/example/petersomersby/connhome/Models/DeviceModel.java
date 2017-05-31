package com.example.petersomersby.connhome.Models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class DeviceModel {

    @IntDef({Type.LIGHT, Type.DOORLOCK, Type.GATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int LIGHT = 1;
        int DOORLOCK = 2;
        int GATE = 3;
    }

    private int id;
    private String description;
    private String title;
    private int pinNumber;
    private int client_id;
    private int type;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public int getId() {return id; }

    public void setId(int id) { this.id = id; }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
