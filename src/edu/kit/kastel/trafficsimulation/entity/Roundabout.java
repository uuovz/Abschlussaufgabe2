package edu.kit.kastel.trafficsimulation.entity;

public class Roundabout extends Crossing {

    public Roundabout(int id ){
        super(id);
    }

    @Override
    public Street cross(Car car, int preference) {
        return getStreetOfPreference(preference);
    }


}
