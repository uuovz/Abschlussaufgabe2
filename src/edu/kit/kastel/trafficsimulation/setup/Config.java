package edu.kit.kastel.trafficsimulation.setup;

import edu.kit.kastel.trafficsimulation.entity.Car;
import edu.kit.kastel.trafficsimulation.entity.Crossing;
import edu.kit.kastel.trafficsimulation.entity.Road;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private Map<Integer, Crossing> crossings = new HashMap<>();
    private Map<Integer, Road> roads = new HashMap<>();
    private List<Integer> roadPlaceOrder = new ArrayList<>();
    private Map<Integer, Car> cars = new HashMap<>();
    private List<Integer> carPlaceOrder = new ArrayList<>();

    public void setCrossings(Map<Integer, Crossing> crossings) {
        this.crossings = crossings;
    }

    public Map<Integer, Crossing> getCrossings() {
        return Collections.unmodifiableMap(crossings);
    }

    public void setRoads(Map<Integer, Road> roads) {
        this.roads = roads;
    }

    public Map<Integer, Road> getRoads() {
        return Collections.unmodifiableMap(roads);
    }

    public List<Integer> getRoadPlaceOrder() {
        return Collections.unmodifiableList(this.roadPlaceOrder);
    }

    public void setRoadPlaceOrder(List<Integer> roadPlaceOrder) {
        this.roadPlaceOrder = roadPlaceOrder;
    }

    public void setCars(Map<Integer, Car> cars) {
        this.cars = cars;
    }

    public Map<Integer, Car> getCars() {
        return Collections.unmodifiableMap(cars);
    }

    public void setCarPlaceOrder(List<Integer> carPlaceOrder) { this.carPlaceOrder = carPlaceOrder;}

    public List<Integer> getCarPlaceOrder() { return Collections.unmodifiableList(carPlaceOrder);}


}
