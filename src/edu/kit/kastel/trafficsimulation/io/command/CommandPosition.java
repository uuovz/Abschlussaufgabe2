package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.entity.Car;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;
import edu.kit.kastel.trafficsimulation.utility.Position;

import java.util.regex.Pattern;

public class CommandPosition extends Command {

    private static final String EXCEPTION_INVALID_ID = "Cannot find id %s";
    private static final String OUTPUT_POSITION = "Car %d on Street %d with speed %d and position %d";
    private static final String regularExpression = "position" + REGEX_ALL;
    private static final Pattern pattern = Pattern.compile(regularExpression);
    private final Simulation simulation;

    public CommandPosition(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean matches(String commandString) {
        return pattern.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        String idStr = getArgument(commandString);
        int idInt = getPositiveInteger(idStr);
        Car car = simulation.getCarById(idInt);
        if (car == null) {
            throw new SimulationException(String.format(EXCEPTION_INVALID_ID, idStr));
        }
        Position position = car.getPosition();
        return String.format(OUTPUT_POSITION, car.getId(), position.getStreet().getId(),
            car.getSpeed(), position.getMileage());
    }
}
