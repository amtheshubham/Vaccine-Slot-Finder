package com.example.vaccineslotfinder;

import com.google.gson.annotations.SerializedName;

class Sessions{

    @SerializedName("name")
    private String name;

    @SerializedName("min_age_limit")
    private Integer min_age_limit;

    @SerializedName("fee")
    private String fee;

    public String getName() {
        return name;
    }

    public Integer getMin_age_limit() {
        return min_age_limit;
    }

    public String getFee() {
        return fee;
    }
}