package edu.kit.kastel.trafficsimulation.utility;

import edu.kit.kastel.trafficsimulation.entity.Street;

public class Position {

    private int mileage;
    private Street street;

    public Position(Street street) {
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
