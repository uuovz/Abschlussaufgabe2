package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.utility.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CarCollection {

    private final Graph graph;
    private final Map<Integer, Car> idCarMap;
    private final List<Integer> carPlaceOrder;
    private final Map<Position, Car> positionCarMap = new TreeMap<>();

    public CarCollection(Graph graph, Map<Integer, Car> idCarMap, List<Integer> carPlaceOrder) {
        this.graph = graph;
        this.idCarMap = new HashMap<>(idCarMap);
        this.carPlaceOrder = new ArrayList<>(carPlaceOrder);
        for (Car car: idCarMap.values()) {
            this.positionCarMap.put(car.getPosition(), car);
        }
    }

    public void placeCars() {
        for (int id: carPlaceOrder) {
            Car car = this.idCarMap.get(id);
            Position position = car.getPosition();
            position.setMileage(position.getRoad().getStartMileage());
        }
    }

    public List<Car> getCarsOnStreet(Road road) {
        List<Car> carsOnStreet = new ArrayList<>();
        for (Entry<Position, Car> entry : this.positionCarMap.entrySet()) {
            if (entry.getKey().getRoad().equals(road)) {
                carsOnStreet.add(entry.getValue());
            }
        }
        return carsOnStreet;
    }
}
