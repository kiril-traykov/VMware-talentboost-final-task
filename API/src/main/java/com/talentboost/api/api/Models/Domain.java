package com.talentboost.api.api.Models;

public class Domain {

    private String name;
    private String address;

    public Domain() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Domain(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
