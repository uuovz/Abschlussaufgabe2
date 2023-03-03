package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.SimulationException;

import java.util.ArrayList;
import java.util.List;

public abstract class Crossing {

    private static final int MAX_ALLOWED_STREETS = 4;
    private static final String EXCEPTION_DEBUG = "Missing incoming/outgoing street.";
    private static final String EXCEPTION_MAX_ALLOWED_EDGES = "Only " + MAX_ALLOWED_STREETS +
        " streets incoming/outgoing from crossing";
    private final int id;
    private final List<Road> incomingRoads = new ArrayList<>();
    private final List<Road> outgoingRoads = new ArrayList<>();

    Crossing(int id) { this.id = id; }
    public abstract Road cross(int index);

    public int getId() {
        return id;
    }

    public void addIncomingRoad(Road road) {
        if (this.incomingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.incomingRoads.add(road);
    }

    public void addOutgoingRoad(Road road) {
        if (this.outgoingRoads.size() + 1 > MAX_ALLOWED_STREETS) {
            throw new SimulationException(EXCEPTION_MAX_ALLOWED_EDGES);
        }
        this.outgoingRoads.add(road);
    }

    public void debug() {
        if (this.incomingRoads.isEmpty() || this.outgoingRoads.isEmpty()) {
            throw new SimulationException(EXCEPTION_DEBUG);
        }
    }

    public Road getIncomingRoad(int index) { return this.incomingRoads.get(index); }

    public Road getOutgoingRoad(int index) { return this.outgoingRoads.get(index); }
}
