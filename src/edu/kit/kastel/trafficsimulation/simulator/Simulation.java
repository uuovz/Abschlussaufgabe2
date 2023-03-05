package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.Car;
import edu.kit.kastel.trafficsimulation.entity.CarCollection;
import edu.kit.kastel.trafficsimulation.entity.Graph;
import edu.kit.kastel.trafficsimulation.setup.Config;
import edu.kit.kastel.trafficsimulation.utility.Tick;


public class Simulation {

    private boolean configured = false;
    private CarCollection carCollection;
    private final Tick tick = new Tick();

    public void configure(Config config) {
        this.tick.reset();
        this.carCollection = new CarCollection(config.getCars(), config.getCarPlaceOrder());
        Graph graph = new Graph(this.tick, this.carCollection, config.getStreets(), config.getRoadPlaceOrder(), config.getCrossings());
        graph.connect();
        graph.debug();
        this.carCollection.placeCars();
        this.configured = true;
    }

    public Car getCarById(int id) {
        return this.carCollection.getCarById(id);
    }

    public void simulate() {
        this.carCollection.simulate();
        this.tick.simulate();
    }

    public boolean isConfigured() {
        return this.configured;
    }
}
