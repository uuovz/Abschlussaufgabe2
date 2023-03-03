package edu.kit.kastel.trafficsimulation.entity;

public class SingleLane extends Road {

    public SingleLane(Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateMaxDrivingDistance() {
        return 0;
    }
}
