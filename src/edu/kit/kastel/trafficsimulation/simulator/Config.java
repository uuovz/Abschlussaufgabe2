package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.Car;
import edu.kit.kastel.trafficsimulation.entity.Crossing;
import edu.kit.kastel.trafficsimulation.entity.Street;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private Map<Integer, Crossing> crossings = new HashMap<>();
    private Map<Integer, Street> sreets = new HashMap<>();
    private List<Integer> streetPlaceOrder = new ArrayList<>();
    private Map<Integer, Car> cars = new HashMap<>();
    private List<Integer> carPlaceOrder = new ArrayList<>();

    public void setCrossings(Map<Integer, Crossing> crossings) { this.crossings = new HashMap<>(crossings); }

    public Map<Integer, Crossing> getCrossings() {
        return Collections.unmodifiableMap(crossings);
    }

    public void setStreets(Map<Integer, Street> streets) { this.sreets = new HashMap<>(streets);}

    public Map<Integer, Street> getStreets() {
        return Collections.unmodifiableMap(sreets);
    }

    public List<Integer> getRoadPlaceOrder() {
        return Collections.unmodifiableList(this.streetPlaceOrder);
    }

    public void setStreetPlaceOrder(List<Integer> streetPlaceOrder) {
        this.streetPlaceOrder = new ArrayList<>(streetPlaceOrder);
    }

    public void setCars(Map<Integer, Car> cars) { this.cars = new HashMap<>(cars); }

    public Map<Integer, Car> getCars() {
        return Collections.unmodifiableMap(cars);
    }

    public void setCarPlaceOrder(List<Integer> carPlaceOrder) { this.carPlaceOrder = new ArrayList<>(carPlaceOrder); }

    public List<Integer> getCarPlaceOrder() { return Collections.unmodifiableList(carPlaceOrder);}


}
