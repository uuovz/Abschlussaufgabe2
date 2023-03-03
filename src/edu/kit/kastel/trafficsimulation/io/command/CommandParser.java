package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.io.Executable;
import edu.kit.kastel.trafficsimulation.setup.Config;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandParser implements Executable {

    private static final String EXCEPTION_INVALID = "Your command was invalid.";
    private boolean active = true;
    private final Config config;
    private final Simulation simulation;
    private final Command commandExit = new CommandQuit(this);
    private final List<Command> commands = new ArrayList<>();


    public CommandParser(Config config, Simulation simulation) {
        this.config = config;
        this.simulation = simulation;
        this.commands.add(new CommandQuit(this));
        this.commands.add(new CommandLoad(config, simulation));
    }


    public String parse(String commandString) {
        for (Command command: commands) {
            if (command.matches(commandString)) {
                return command.execute(commandString);
            }
        }
        throw new SimulationException(EXCEPTION_INVALID);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void quit() {
        this.active = false;
    }
}
