package edu.kit.kastel.trafficsimulation.utility;

public class Tick {

    private int tick = 0;

    public void simulate() {
        this.tick += 1;
    }

    public int getTick() {
        return tick;
    }

    public void reset() {
        this.tick = 0;
    }
}
