package com.coronaid.coronaid;

public class CountryData {
    String name, totalCase, totalDeath, newCase, newDeath;
    Integer flagId;


    public CountryData (String name, String totalCase, String totalDeath, String newCase, String newDeath, Integer flagId) {
        this.name = name;
        this.totalCase = totalCase;
        this.totalDeath = totalDeath;
        this.newCase = newCase;
        this.newDeath = newDeath;
        this.flagId = flagId;
    }

    public Integer getFlagId() {
        return flagId;
    }

    public String getNewCase() {
        return newCase;
    }

    public String getNewDeath() {
        return newDeath;
    }

    public String getTotalCase() {
        return totalCase;
    }

    public String getTotalDeath() {
        return totalDeath;
    }

    public String getName() {
        return name;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNewCase(String newCase) {
        this.newCase = newCase;
    }

    public void setNewDeath(String newDeath) {
        this.newDeath = newDeath;
    }

    public void setTotalCase(String totalCase) {
        this.totalCase = totalCase;
    }

    public void setTotalDeath(String totalDeath) {
        this.totalDeath = totalDeath;
    }
}
