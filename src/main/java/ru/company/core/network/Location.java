package ru.company.core.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("street")
    private String street;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String area;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }
}
