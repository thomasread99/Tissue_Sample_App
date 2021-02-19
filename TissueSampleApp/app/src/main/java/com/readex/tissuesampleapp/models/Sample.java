package com.readex.tissuesampleapp.models;

import java.text.SimpleDateFormat;

public class Sample {

    private String materialType;
    private int donorCount;
    private String lastUpdated;

    public Sample () {}

    public Sample(String materialType, int donorCount, String lastUpdated) {
        this.materialType = materialType;
        this.donorCount = donorCount;
        this.lastUpdated = lastUpdated;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public int getDonorCount() {
        return donorCount;
    }

    public void setDonorCount(int donorCount) {
        this.donorCount = donorCount;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
