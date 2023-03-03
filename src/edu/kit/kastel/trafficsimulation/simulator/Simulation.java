package edu.kit.kastel.trafficsimulation.simulator;

import edu.kit.kastel.trafficsimulation.entity.CarCollection;
import edu.kit.kastel.trafficsimulation.entity.Graph;
import edu.kit.kastel.trafficsimulation.setup.Config;



public class Simulation {

    private boolean configured = false;
    private Graph graph;
    private CarCollection carCollection;

    public void configure(Config config) {
        this.carCollection = new CarCollection(this.graph, config.getCars(), config.getCarPlaceOrder());
        this.graph = new Graph(carCollection, config.getRoads(), config.getRoadPlaceOrder(), config.getCrossings());
        this.graph.connect();
        this.graph.debug();
    }

    public boolean isConfigured() {
        return this.configured;
    }
}
