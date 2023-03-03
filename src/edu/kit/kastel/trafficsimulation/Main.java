package edu.kit.kastel.trafficsimulation;

import edu.kit.kastel.trafficsimulation.io.Shell;

/**
 * The application and entry point of the program.
 *
 * @author uuovz
 * @version 1.0
 */
public final class Main {

    private static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ERROR_COMMAND_ARGUMENTS = "No Command Arguments Allowed.";
    private static final String ERROR_START = "Error:";

    private Main() {throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION); }

    /**
     * Starts the Shell for rover and user input.
     * @param args have to be empty
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            Shell shell = new Shell();
            shell.start();
        } else {
            System.err.println(ERROR_START + ERROR_COMMAND_ARGUMENTS);
        }

    }

}
