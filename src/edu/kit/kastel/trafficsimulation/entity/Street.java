package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.SimulationException;

import java.util.List;

public abstract class Street {

    protected static final int MIN_DISTANCE = 10;
    private static final String EXCEPTION_TEMPLATE_TO_MANY_CARS = "To many cars on street %d";
    protected final Crossing startCrossing;
    protected final Crossing endCrossing;
    private final int maximumSpeed;
    protected CarCollection carCollection;
    private final int id;
    private final int length;
    private int nextStartPosition;

    public Street(int id, Crossing startCrossing, Crossing endCrossing, int length, int maximumSpeed) {
        this.id = id;
        this.startCrossing = startCrossing;
        this.endCrossing = endCrossing;
        this.length = length;
        this.nextStartPosition = length;
        this.maximumSpeed = maximumSpeed;
    }

    public abstract int calculateDrivingDistance(Car car, int distance);
    public abstract boolean didCarOvertake(Car car);

    public int getLength() { return length; }

    public Crossing getStartCrossing() { return startCrossing; }

    public Crossing getEndCrossing() { return endCrossing; }

    public int getMaximumSpeed() { return maximumSpeed; }

    public int getStartMileage() {
        int startPosition = nextStartPosition;
        if (startPosition < 0) {
            throw new SimulationException(EXCEPTION_TEMPLATE_TO_MANY_CARS);
        }
        this.nextStartPosition -= MIN_DISTANCE;
        return startPosition;
    }

    public int getId() {
        return id;
    }

    public void setCarCollection(CarCollection carCollection) {
        this.carCollection = carCollection;
    }

    public int calculateGetOnStreetDistance(int distance) {
        List<Car> carsOnStreet = this.carCollection.getCarsOnStreet(this);
        Car lastCar = getLastCar(carsOnStreet);
        if (lastCar == null) {
            return Math.min(distance, this.getLength());
        }
        return getDriveDistance(lastCar.getPosition().getMileage(), distance);
    }

    protected static int getDistanceBetweenCars(Car carA, Car carB) {
        return Math.abs(carA.getPosition().getMileage() - carB.getPosition().getMileage());
    }

    protected static Car getNextCar(List<Car> carsOnStreet, Car car) {
        int indexOfCar = carsOnStreet.indexOf(car);
        //no car before
        if (indexOfCar == 0) {
            return null;
        }
        return carsOnStreet.get(carsOnStreet.indexOf(car) - 1);
    }

    protected static Car getLastCar(List<Car> carsOnStreet) {
        if (carsOnStreet.isEmpty()) {
            return null;
        }
        return carsOnStreet.get(carsOnStreet.size() - 1);
    }

    protected static int getDriveDistance(int distanceBetweenCars, int distance) {
        return Math.min(distanceBetweenCars - MIN_DISTANCE, distance);
    }


}
