package edu.kit.kastel.trafficsimulation.entity;

import java.util.List;

public class TwoLane extends Street {

    private Car lastCarOvertake = null;

    public TwoLane(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(id, startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateDrivingDistance(Car car, int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car nextCar = getNextCar(carsOnStreet, car);
        Car nextNextCar = getNextCar(carsOnStreet, car);
        if (nextCar == null) {
            return distance;
        }
        int nextCarMileage = nextCar.getPosition().getMileage();
        int nextNextCarMileage = nextNextCar.getPosition().getMileage();
        if (distance > nextCarMileage + MIN_DISTANCE && distance < nextNextCarMileage - MIN_DISTANCE) {
            this.lastCarOvertake = car;
            return getDriveDistance(getDistanceBetweenCars(car, nextNextCar), distance);
        }
        return getDriveDistance(getDistanceBetweenCars(car, nextCar), distance);
    }

    @Override
    public int calculateGetOnStreetDistance(int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car lastCar = getLastCar(carsOnStreet);
        if (lastCar == null) {
            return Math.min(distance, this.getLength());
        }
        return getDriveDistance(lastCar.getPosition().getMileage(), distance);
    }

    @Override
    public boolean didCarOvertake(Car car) {
        return car.equals(this.lastCarOvertake);
    }
}
