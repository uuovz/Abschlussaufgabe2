package edu.kit.kastel.trafficsimulation.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarCollection {

    private final Map<Integer, Car> idCarMap;
    private final List<Integer> carPlaceOrder;
    private final Comparator<Car> carPositionComparator = new CarPositionComparator();

    public CarCollection(Map<Integer, Car> idCarMap, List<Integer> carPlaceOrder) {
        this.idCarMap = new HashMap<>(idCarMap);
        this.carPlaceOrder = new ArrayList<>(carPlaceOrder);
    }

    public void placeCars() {
        for (int id: carPlaceOrder) {
            Car car = this.idCarMap.get(id);
            Position position = car.getPosition();
            position.setMileage(position.getStreet().getStartMileage());
        }
    }

    public List<Car> getCarsOnStreet(Street street) {
        List<Car> carsOnStreet = new ArrayList<>();
        for (Car car: idCarMap.values()) {
            if (car.getPosition().getStreet().equals(street)) {
                carsOnStreet.add(car);
            }
        }
        Collections.sort(carsOnStreet, this.carPositionComparator);
        return carsOnStreet;
    }

    public Car getCarById(int id) {
        return this.idCarMap.get(id);
    }

    public void simulate() {
        List<Car> allCars = new ArrayList<>(idCarMap.values());
        Collections.sort(allCars, this.carPositionComparator);
        for (Car car: allCars) {
            car.simulate();
        }
        for (int i = 0; i < 34; i++) {
            Car car = getCarById(i);
            System.out.println(String.format("Car %d on street %d with speed %d and position %d", car.getId(), car.getPosition().getStreet().getId(), car.getSpeed(), car.getPosition().getMileage()));
        }

    }
}
