package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.data.Registry;
import ai.prima.prima.exceptions.NoRegistryEntryException;
import ai.prima.prima.util.Log;

public class HelpCommand extends Command {

    private static final String COMMAND = "command";

    public HelpCommand() {
        super("help", "See available commands and their syntax",
                Arrays.asList(),
                Arrays.asList(COMMAND),
                Arrays.asList());
    }

    @Override
    public void execute(Map<String, String> args) {
        if (args.containsKey(COMMAND)) {
            printUsage(args.get(COMMAND));
        } else {
            printCommands();
        }
    }

    private void printUsage(String cmd) {
        try {
            Command command = Registry.getCommand(cmd);
            Log.writeLine(command.getCmd() + ": \n"
                    + "Description: " + command.getDescription() + "\n"
                    + "Syntax: " + command.getUsage());
        } catch (NoRegistryEntryException e) {
            Log.writeLine("Help: Unknown command");
        }
    }

    private void printCommands() {
        Registry.getCommandRegistry().values().forEach(command -> {
            Log.writeLine(command.getCmd() + " - " + command.getDescription());
        });
        Log.writeLine("\n[required parameter] <optional parameter>");
        Log.writeLine("\nuse -[parameter] [value] to specifically set a parameter");
        Log.writeLine("\nType 'help <command>' do see detail information");
    }
}
