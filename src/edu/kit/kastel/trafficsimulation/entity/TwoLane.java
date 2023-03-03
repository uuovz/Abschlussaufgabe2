package edu.kit.kastel.trafficsimulation.entity;

public class TwoLane extends Road {

    public TwoLane(Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateMaxDrivingDistance() {
        return 0;
    }
}
