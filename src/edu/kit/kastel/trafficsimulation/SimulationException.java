package edu.kit.kastel.trafficsimulation;

/**
 * The SimulationException class is a custom exception that extends the IllegalArgumentException class.
 * It is used to handle errors related to game operations and has a constructor that takes a message parameter
 *
 * @author uuovz
 * @version 1.0
 */
public class SimulationException extends IllegalArgumentException {
    /**
     * The start of an output string for a failed operation.
     */
    private static final String ERROR_START = "Error: ";

    /**
     * The end of an output string for a failed operation.
     */
    private static final String ERROR_END = " Please try again!";

    /**
     * Instantiates a new {@link SimulationException} with the give message.
     *
     * @param message the message of the exception
     */
    public SimulationException(final String message) {
        super(ERROR_START + message + ERROR_END);
    }
}
