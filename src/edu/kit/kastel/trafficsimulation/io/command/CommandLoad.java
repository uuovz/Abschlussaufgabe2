package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.Car;
import edu.kit.kastel.trafficsimulation.entity.Crossing;
import edu.kit.kastel.trafficsimulation.entity.Intersection;
import edu.kit.kastel.trafficsimulation.entity.Roundabout;
import edu.kit.kastel.trafficsimulation.entity.SingleLane;
import edu.kit.kastel.trafficsimulation.entity.Street;
import edu.kit.kastel.trafficsimulation.entity.TwoLane;
import edu.kit.kastel.trafficsimulation.io.SimulationFileLoader;
import edu.kit.kastel.trafficsimulation.simulator.Config;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;
import edu.kit.kastel.trafficsimulation.entity.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandLoad extends Command {
    private static final String regularExpression = "load" + REGEX_ALL;
    private static final Pattern pattern = Pattern.compile(regularExpression);
    private static final String SUFFIX_TIME = "t";
    private static final String SEPERATOR_CROSSING_STREET = ":";
    private static final int MIN_CAR_SPEED = 20;
    private static final int MAX_CAR_SPEED = 40;
    private static final int MIN_ACCELERATION = 1;
    private static final int MAX_ACCELERATION = 10;
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 10000;
    private static final int MIN_STREET_SPEED = 5;
    private static final int MAX_STREET_SPEED = 40;
    private static final int ARGUMENT_CROSSING_AFTER_SPLIT = 2;
    private static final int INDEX_ID = 0;
    private static final int INDEX_CROSSING_GREENPHASE = 1;
    private static final int ROUNDABOUT_GREENPHASE_DURATION = 0;
    private static final int ARGUMENT_ROAD_AFTER_SPLIT = 2;
    private static final int INDEX_STREET_NODES = 0;
    private static final int INDEX_STREET_PROP = 1;
    private static final int INDEX_STREET_START_NODE = 0;
    private static final int INDEX_STREET_END_NODE = 1;
    private static final int INDEX_STREET_LENGTH = 0;
    private static final int INDEX_STREET_LANES = 1;
    private static final int INDEX_STREET_MAX_SPEED = 2;
    private static final int SINGLE_LANE = 1;
    private static final String SEPERATOR_NODES = "-->";
    private static final String SEPERATOR_STREET_CARS = ",";
    private static final String REPLACE_LENGTH = "m";
    private static final String REPLACE_LANES = "x";
    private static final String REPLACE_MAX = "max";
    private static final int ARGUMENTS_CAR = 4;
    private static final int INDEX_CAR_STREET_ID = 1;
    private static final int INDEX_CAR_MAX_SPEED = 2;
    private static final int INDEX_CAR_ACCELERATION = 3;
    private static final String EXCEPTION_TEMPLATE__ARGUMENT_AMOUNT = "Invalid argument amount in the %s dataset";
    private static final String EXCEPTION_TEMPLATE_DUPLICATED_ID = "Duplicated IDs in %s.";
    private static final String EXCEPTION_TEMPLATE_INVALID_ID = "Invalid id in %s dataset";
    private static final String EXCEPTION_TEMPLATE_SPEED_RANGE = "Speed of car with id %d not in range.";
    private static final String EXCEPTION_TEMPLATE_ACCELERATION_RANGE = "Accelertion of car with id %d not in range.";
    private static final String EXCEPTION_TEMPLATE_LENGTH = "Length of street with id %d not in range";
    private static final String EXCEPTION_TEMPLATE_SPEED = "Speed of street with id %d not in range";
    private final Config config;
    private final Simulation simulation;
    private SimulationFileLoader simulationFileLoader;
    private final Map<Integer, Crossing> crossings = new HashMap<>();
    private final Map<Integer, Street> streets = new HashMap<>();
    private final List<Integer> streetPlaceOrder = new ArrayList<>();
    private final Map<Integer, Car> cars = new HashMap<>();
    private final List<Integer> carPlaceOrder = new ArrayList<>();

    public CommandLoad(Config config, Simulation simulation) {
        this.config = config;
        this.simulation = simulation;
    }

    @Override
    public boolean matches(String commandString) {
        return pattern.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        this.clearAll();
        try {
            this.simulationFileLoader = new SimulationFileLoader(getArgument(commandString));
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        this.setUpCrossing();
        this.setUpStreet();
        this.setUpCars();
        this.config.setCrossings(this.crossings);
        this.config.setStreets(this.streets);
        this.config.setStreetPlaceOrder(this.streetPlaceOrder);
        this.config.setCars(this.cars);
        this.config.setCarPlaceOrder(this.carPlaceOrder);
        this.simulation.configure(this.config);
        return OUTPUT_MESSAGE_READY;
    }

    private void clearAll() {
        this.crossings.clear();
        this.streets.clear();
        this.cars.clear();
        this.streetPlaceOrder.clear();
        this.carPlaceOrder.clear();
    }

    private void setUpCrossing() {
        List<String> crossingData;
        try {
            crossingData = this.simulationFileLoader.loadCrossings();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        for (String crossing: crossingData) {
            String cleanInput = crossing.replace(SUFFIX_TIME, EMPTY_STRING);
            String[] arguments = cleanInput.split(SEPERATOR_CROSSING_STREET);
            if (arguments.length == ARGUMENT_CROSSING_AFTER_SPLIT) {
                int id =  getPositiveInteger(arguments[INDEX_ID]);
                int greenPhaseDuration = getPositiveInteger(arguments[INDEX_CROSSING_GREENPHASE]);
                addCrossing(id, greenPhaseDuration);
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE__ARGUMENT_AMOUNT, "crossing"));
            }
        }
    }
    private void addCrossing(int id, int greenPhase) {
        if (crossings.get(id) == null) {
            if (greenPhase == ROUNDABOUT_GREENPHASE_DURATION) {
                this.crossings.put(id, new Roundabout(id));
            } else
                this.crossings.put(id, new Intersection(id, greenPhase));
        }
        else {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_DUPLICATED_ID, "crossing"));
        }
    }


    private void setUpStreet() {
        List<String> streetData;
        try {
            streetData = this.simulationFileLoader.loadStreets();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        int id = 0;
        int startNode;
        int endNode;
        int length;
        int lanes;
        int maxSpeed;
        for (String street: streetData) {
            String[] arguments = street.split(SEPERATOR_CROSSING_STREET);
            if (arguments.length == ARGUMENT_ROAD_AFTER_SPLIT) {
                String[] nodeArguments = arguments[INDEX_STREET_NODES].split(SEPERATOR_NODES);
                if (nodeArguments.length == ARGUMENT_ROAD_AFTER_SPLIT) {
                    startNode = getPositiveInteger(nodeArguments[INDEX_STREET_START_NODE]);
                    endNode = getPositiveInteger(nodeArguments[INDEX_STREET_END_NODE]);
                } else {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE__ARGUMENT_AMOUNT, "street"));
                }
                String[] propArguments = arguments[INDEX_STREET_PROP].split(SEPERATOR_STREET_CARS);
                length = getPositiveInteger(propArguments[INDEX_STREET_LENGTH].replace(REPLACE_LENGTH, EMPTY_STRING));
                if (length < MIN_LENGTH || length > MAX_LENGTH) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_LENGTH, id));
                }
                maxSpeed = getPositiveInteger(propArguments[INDEX_STREET_MAX_SPEED].replace(REPLACE_MAX, EMPTY_STRING));
                if (maxSpeed < MIN_STREET_SPEED || maxSpeed > MAX_STREET_SPEED) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SPEED, id));
                }
                lanes = getPositiveInteger(propArguments[INDEX_STREET_LANES].replace(REPLACE_LANES, EMPTY_STRING));
                this.addStreet(id, startNode, endNode, length, lanes, maxSpeed);
                id++;
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE__ARGUMENT_AMOUNT, "street"));
            }
        }

    }
    private void addStreet(int id, int startNode, int endNode, int length, int lanes, int maxSpeed) {
        Crossing startCrossing = this.crossings.get(startNode);
        Crossing endCrossing = this.crossings.get(endNode);
        if (startCrossing == null || endCrossing == null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_INVALID_ID, "street"));
        }
        streetPlaceOrder.add(id);
        if (lanes == SINGLE_LANE) {
            streets.put(id, new SingleLane(id, startCrossing, endCrossing, length, maxSpeed));
        } else {
            streets.put(id, new TwoLane(id, startCrossing, endCrossing, length, maxSpeed));
        }
    }

    private void setUpCars() {
        List<String> carData;
        try {
            carData = this.simulationFileLoader.loadCars();
        } catch (IOException ioException) {
            throw new SimulationException(ioException.getMessage());
        }
        for (String car: carData) {
            String[] arguments = car.split(SEPERATOR_STREET_CARS);
            if (arguments.length == ARGUMENTS_CAR) {
                int id = getPositiveInteger(arguments[INDEX_ID]);
                int roadId = getPositiveInteger(arguments[INDEX_CAR_STREET_ID]);
                int maxSpeed = getPositiveInteger(arguments[INDEX_CAR_MAX_SPEED]);
                if (maxSpeed < MIN_CAR_SPEED || maxSpeed > MAX_CAR_SPEED ) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_SPEED_RANGE, id));
                }
                int acceleration = getPositiveInteger(arguments[INDEX_CAR_ACCELERATION]);
                if (acceleration < MIN_ACCELERATION || acceleration > MAX_ACCELERATION ) {
                    throw new SimulationException(String.format(EXCEPTION_TEMPLATE_ACCELERATION_RANGE, id));
                }
                this.addCar(id, roadId, maxSpeed, acceleration);
            } else {
                throw new SimulationException(String.format(EXCEPTION_TEMPLATE__ARGUMENT_AMOUNT, "car"));
            }
        }
    }

    private void addCar(int id, int streetId, int maxSpeed, int accelaration) {
        Car car = cars.get(id);
        if (car != null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_DUPLICATED_ID, "car"));
        }
        Street street = streets.get(streetId);
        if (street == null) {
            throw new SimulationException(String.format(EXCEPTION_TEMPLATE_INVALID_ID, "car"));
        }
        this.carPlaceOrder.add(id);
        Position position = new Position(street);
        this.cars.put(id, new Car(id, position, maxSpeed, accelaration));
    }

}
