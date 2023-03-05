package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.utility.Tick;

import java.util.ArrayList;
import java.util.List;

public abstract class Crossing {

    public static final int MAX_ALLOWED_STREETS = 4;
    private static final String EXCEPTION_DEBUG = "Missing incoming/outgoing street.";
    private static final String EXCEPTION_MAX_ALLOWED_EDGES = "Only " + MAX_ALLOWED_STREETS +
        " streets incoming/outgoing from crossing";
    protected final List<Street> incomingRoads = new ArrayList<>();
    protected final List<Street> outgoingRoads = new ArrayList<>();
    protected Tick tick;
    private final int id;

    Crossing(int id) { this.id = id; }
    public abstract Street cross(Car car, int index);

    public void addIncomingStreet(Street street) {
        if (this.incomingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.incomingRoads.add(street);
    }

    public void addOutgoingStreet(Street street) {
        if (this.outgoingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.outgoingRoads.add(street);
    }

    public void debug() {
        if (this.incomingRoads.isEmpty() || this.outgoingRoads.isEmpty()) {
            throw new SimulationException(EXCEPTION_DEBUG);
        }
    }

    protected Street getStreetOfPreference(int preference) {
        if (preference >= this.outgoingRoads.size()) {
            return this.outgoingRoads.get(0);
        }
        return this.outgoingRoads.get(preference);
    }

    public int getId() {
        return id;
    }

    public void setTick(Tick tick) {
        this.tick = tick;
    }

}
