package network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DateOfBirth implements Serializable {

    @SerializedName("date")
    private String date;

    @SerializedName("age")
    private int age;

    public String getDate() {
        return date;
    }

    public int getAge() {
        return age;
    }
}
