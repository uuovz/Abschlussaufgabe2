package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.utility.Tick;

public class Intersection extends Crossing {
    private final int greenphaseDuration;

    public Intersection(int id, int greenphaseDuration){
        super(id);
        this.greenphaseDuration = greenphaseDuration;
    }

    @Override
    public Street cross(Car car, int preference) {
        int greeLightStreetIndex = greenLightStreetIndex();
        if (car.getPosition().getStreet().equals(this.incomingRoads.get(greeLightStreetIndex))) {
            return this.getStreetOfPreference(preference);
        }
        return null;
    }

    private int greenLightStreetIndex() {
        return this.tick.getTick() / this.greenphaseDuration % this.incomingRoads.size();
    }


}
