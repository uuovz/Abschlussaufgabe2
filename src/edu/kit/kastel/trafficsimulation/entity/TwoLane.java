package edu.kit.kastel.trafficsimulation.entity;

import java.util.List;

public class TwoLane extends Street {

    private Car lastCarOvertake = null;

    public TwoLane(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        super(id, startCrossing, endCrossing, length, maximumSpeed);
    }

    @Override
    public int calculateDrivingDistance(Car car, int distance) {
        lastCarOvertake = null;
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car nextCar = getNextCar(carsOnStreet, car);
        if (nextCar == null) {
            return distance;
        }
        Car nextNextCar = getNextCar(carsOnStreet, nextCar);
        if (nextNextCar == null && distance >= getDistanceBetweenCars(car ,nextCar) + MIN_DISTANCE) {
            return distance;
        }
        if (distance >= getDistanceBetweenCars(car ,nextCar) + MIN_DISTANCE && nextNextCar != null &&
            getDistanceBetweenCars(car ,nextCar) + MIN_DISTANCE <=
                getDistanceBetweenCars(car, nextNextCar) - MIN_DISTANCE ) {
            this.lastCarOvertake = car;
            return getDriveDistance(getDistanceBetweenCars(car, nextNextCar), distance);
        }

        return getDriveDistance(getDistanceBetweenCars(car, nextCar), distance);
    }

    @Override
    public boolean didCarOvertake(Car car) {
        return car.equals(this.lastCarOvertake);
    }
}
