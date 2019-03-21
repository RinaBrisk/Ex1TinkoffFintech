package ru.company.core.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PersonsDTO implements Serializable {

    @SerializedName("gender")
    private String gender;

    @SerializedName("name")
    private Name name;

    @SerializedName("location")
    private Location location;

    @SerializedName("dob")
    private DateOfBirth dateOfBirth;

    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }
}
