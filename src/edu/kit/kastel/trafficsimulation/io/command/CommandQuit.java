package edu.kit.kastel.trafficsimulation.io.command;

import java.util.regex.Pattern;

public class CommandQuit extends Command {

    private static final String regularExpression = "quit";
    private static final Pattern pattern = Pattern.compile(regularExpression);
    private final CommandParser commandParser;

    public CommandQuit(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public boolean matches(String commandString) {
        return pattern.matcher(commandString).matches();
    }

    @Override
    public String execute(String commandString) {
        this.commandParser.quit();
        return null;
    }
}
