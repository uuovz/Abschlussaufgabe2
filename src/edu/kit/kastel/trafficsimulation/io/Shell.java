package edu.kit.kastel.trafficsimulation.io;

import edu.kit.kastel.trafficsimulation.SimulationException;
import edu.kit.kastel.trafficsimulation.io.command.CommandParser;
import edu.kit.kastel.trafficsimulation.setup.Config;
import edu.kit.kastel.trafficsimulation.simulator.Simulation;

import java.util.Scanner;

/**
 *
 * @author uuovz
 * @version 1.0
 */
public class Shell {
    private final Config config = new Config();
    private final Simulation simulation = new Simulation();
    private final CommandParser commandParser = new CommandParser(config, simulation);

    public void start() {

        Scanner scanner = new Scanner(System.in);
        while(commandParser.isActive()) {
            try {
                String commandString = scanner.nextLine();
                String output = commandParser.parse(commandString);
                if (output != null) {
                    System.out.println(output);
                }
            } catch (SimulationException exception) {
                System.out.println(exception.getMessage());
            }
        }
        scanner.close();
    }
}
