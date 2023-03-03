package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.SimulationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private static final  String CONNECTION_ERROR = "Failure in dataset";
    private final Map<Integer, Road> idRoadMap;
    private final List<Integer> edgePlaceOrder;
    private final Map<Integer, Crossing> idCrossingMap;
    private final CarCollection carCollection;

    public Graph(CarCollection carCollection, Map<Integer, Road> idRoadMap, List<Integer> roadPlaceOrder,
                 Map<Integer, Crossing> idCrossingMap) {
        this.carCollection = carCollection;
        this.idRoadMap = new HashMap<>(idRoadMap);
        this.edgePlaceOrder = new ArrayList<>(roadPlaceOrder);
        this.idCrossingMap = new HashMap<>(idCrossingMap);
    }

    public void connect() {
        for (Integer id: edgePlaceOrder) {
            Road road = this.idRoadMap.get(id);
            road.setCarCollection(this.carCollection);
            Crossing outGoingcrossing = road.getStartCrossing();
            Crossing incomingGoingcrossing = road.getEndCrossing();
            if (outGoingcrossing == null || incomingGoingcrossing == null) {
                throw new SimulationException(CONNECTION_ERROR);
            }
            outGoingcrossing.addOutgoingRoad(road);
            incomingGoingcrossing.addIncomingRoad(road);

        }
    }

    public void debug() {
        for (Crossing crossing: this.idCrossingMap.values()) {
            crossing.debug();
        }
    }

}
