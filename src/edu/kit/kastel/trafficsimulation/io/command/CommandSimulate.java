package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.regex.Pattern;

public class CommandSimulate extends Command {
    private static final String regularExpression = "simulate" + REGEX_ALL;
    private static final Pattern pattern = Pattern.compile(regularExpression);
    private final Simulation simulation;

    public CommandSimulate(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public boolean matches(String commandString) {
        return pattern.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        if (!simulation.isConfigured()) {
            throw new SimulationException(EXCEPTION_CONFIGURED);
        }
        String argument = getArgument(commandString);
        int steps = getPositiveInteger(argument);
        for (int i = 0; i < steps; i++) {
            simulation.simulate();
        }
        return OUTPUT_MESSAGE_READY;
    }
}
