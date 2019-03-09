package network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Name implements Serializable {

    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
