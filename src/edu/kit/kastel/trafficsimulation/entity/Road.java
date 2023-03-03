package edu.kit.kastel.trafficsimulation.entity;

import java.util.List;

public abstract class Road {

    protected final Crossing startCrossing;
    protected final Crossing endCrossing;

    protected final int length;
    protected final int maximumSpeed;
    protected CarCollection carCollection;

    public Road(Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        this.startCrossing = startCrossing;
        this.endCrossing = endCrossing;
        this.length = length;
        this.maximumSpeed = maximumSpeed;
    }

    public abstract int calculateMaxDrivingDistance();

    public Crossing getStartCrossing() { return startCrossing; }

    public Crossing getEndCrossing() { return endCrossing; }

    public int getStartMileage() {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
    }



    public void setCarCollection(CarCollection carCollection) {
        this.carCollection = carCollection;
    }
}
