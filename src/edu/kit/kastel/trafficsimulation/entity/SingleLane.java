package edu.kit.kastel.trafficsimulation.entity;

import java.util.List;

public class SingleLane extends Street {

    public SingleLane(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(id, startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateDrivingDistance(Car car, int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car nextCar = getNextCar(carsOnStreet, car);
        if (nextCar == null) {
            return distance;
        }
        return getDriveDistance(getDistanceBetweenCars(car, nextCar), distance);
    }

    @Override
    public boolean didCarOvertake(Car car) {
        return false;
    }
}
