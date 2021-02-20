package com.readex.tissuesampleapp.models;

/**
 * Custom sample item model
 */
public class Sample {

    private String materialType;
    private int donorCount;
    private String lastUpdated;

    public Sample () {}

    /**
     * Constructor to assign the values
     * @param materialType
     * @param donorCount
     * @param lastUpdated
     */
    public Sample(String materialType, int donorCount, String lastUpdated) {
        this.materialType = materialType;
        this.donorCount = donorCount;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Getters and setters
     */
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
