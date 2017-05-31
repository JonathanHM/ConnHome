package com.example.petersomersby.connhome.Models;

import java.util.List;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class ScenarioModel {

    private int id;
    private String name;
    private String description;
    private List<Integer> device_ids;

    public List<Integer> getDevice_ids() {
        return device_ids;
    }

    public void setDevice_ids(List<Integer> device_ids) {
        this.device_ids = device_ids;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
