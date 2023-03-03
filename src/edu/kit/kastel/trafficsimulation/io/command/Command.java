package edu.kit.kastel.trafficsimulation.io.command;

import edu.kit.kastel.trafficsimulation.SimulationException;

import java.io.IOException;

public abstract class Command {

    private static final String ARGUMENT_SEPERATOR = " ";
    protected static final String REGEX_ALL = ".*";
    protected static final String EMPTY_STRING = "";
    private static final int ARGUMENT_INDEX = 1;
    private static final int ARGUMENT_COUNT = 2;
    private static final String EXCEPTION_ARGUMENT = "Only one Argument allowed.";
    private static final String EXCEPTION_NUMBER = "There is an number format error in the documents.";

    public abstract boolean matches(String commandString);
    public abstract String execute(String commandString) throws IOException;

    protected static String getArgument(String commandString) {
        String[] splitedArguments = commandString.split(ARGUMENT_SEPERATOR);
        if (splitedArguments.length == ARGUMENT_COUNT) {
            return splitedArguments[ARGUMENT_INDEX];
        } else {
            throw(new IllegalArgumentException(EXCEPTION_ARGUMENT));
        }
    }

    protected static int getPositiveInteger(String stringInteger) {
        try {
            int number = Integer.parseInt(stringInteger);
            if (number >= 0) {
                return number;
            } else {
                throw new SimulationException(EXCEPTION_NUMBER);
            }
        } catch (NumberFormatException exception) {
            throw new SimulationException(EXCEPTION_NUMBER);
        }
    }
}
