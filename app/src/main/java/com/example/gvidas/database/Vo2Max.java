package com.example.gvidas.database;

public class Vo2Max {

    public double vo2maxID;
    public double rateMax;
    public double rateRest;
    public double vo2max;
    public String dateOfCalculations;

    public Vo2Max(double vo2maxID, double rateMax, double rateRest, double vo2max, String dateOfCalculations) {
        this.vo2maxID = vo2maxID;
        this.rateMax = rateMax;
        this.rateRest = rateRest;
        this.vo2max = vo2max;
        this.dateOfCalculations = dateOfCalculations;
    }

    public double getVo2maxID() {
        return vo2maxID;
    }

    public double getRateMax() {
        return rateMax;
    }

    public double getRateRest() {
        return rateRest;
    }

    public double getVo2max() {
        return vo2max;
    }

    public String getDateOfCalculations() {
        return dateOfCalculations;
    }
}
