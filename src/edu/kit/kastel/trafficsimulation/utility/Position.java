package edu.kit.kastel.trafficsimulation.utility;

import edu.kit.kastel.trafficsimulation.entity.Road;

public class Position {

    private int mileage;
    private Road road;

    public Position(Road road) {
        this.road = road;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road street) {
        this.road = street;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
