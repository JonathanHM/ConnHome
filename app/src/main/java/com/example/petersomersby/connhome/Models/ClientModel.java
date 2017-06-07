package com.example.petersomersby.connhome.Models;

/**
 * Created by Jonathan on 07-06-2017.
 */

public class ClientModel {

    private int id;
    private String title;
    private String ip_address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
