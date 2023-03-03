package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.utility.Position;

public class Car {

    private final int id;
    private final Position position;
    private final int maxSpeed;
    private final int acceleration;

    public Car(int id, Position position, int maxSpeed, int acceleration) {
        this.id = id;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return this.position;
    }
}
