package edu.kit.kastel.trafficsimulation.entity;

public class Roundabout extends Crossing {

    public Roundabout(int id ){
        super(id);
    }

    @Override
    public Road cross(int i) {
        return null;
    }


}
