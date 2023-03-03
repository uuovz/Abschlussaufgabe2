package edu.kit.kastel.trafficsimulation.entity;

public class Intersection extends Crossing {
    private final int greenphaseDuration;

    public Intersection(int id, int greenphaseDuration){
        super(id);
        this.greenphaseDuration = greenphaseDuration;
    }

    @Override
    public Road cross(int i) {
        return null;
    }
}
