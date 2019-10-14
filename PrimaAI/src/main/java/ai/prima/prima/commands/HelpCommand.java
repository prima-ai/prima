package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ai.prima.prima.data.Registry;
import ai.prima.prima.exceptions.NoRegistryEntryException;
import ai.prima.prima.util.Log;

public class HelpCommand extends Command {

    private static final Parameter COMMAND_PARAMETER = new Parameter("command", Parameter.Requirement.OPTIONAL);

    public HelpCommand() {
        super("help", "See available commands and their syntax", Arrays.asList(COMMAND_PARAMETER));
    }

    @Override
    public void execute(Map<Parameter, String> values) {
        if (values.containsKey(COMMAND_PARAMETER)) {
            printUsage(values.get(COMMAND_PARAMETER));
        } else {
            printCommands();
        }
    }

    private void printUsage(String cmd) {
        Optional<Command> query = Registry.getCommandRegistry().stream().filter(entry -> entry.getShortcut().equals(cmd)).findFirst();
        if(query.isPresent()){
            Command command = query.get();
            Log.writeLine(command.getShortcut() + ": \n"
                    + "Description: " + command.getDescription() + "\n"
                    + "Syntax: " + usageString(command));
        } else {
            Log.writeLine("Help: Unknown command");
        }
    }

    private String usageString(Command command) {
        StringBuilder usage = new StringBuilder("\n" + commandUsage(command));
        command.getSubCommands().forEach(subCommand -> {
            usage.append("\n" + command.getShortcut() + " "  + commandUsage(subCommand));
        });
        return usage.toString();
    }

    private String commandUsage(Command command) {
        StringBuilder usage = new StringBuilder(command.getShortcut());
        command.getUsage().forEach(parameter -> {
            if(parameter.getRequirement() == Parameter.Requirement.REQUIRED){
                usage.append(" [" + parameter.getName() + "]");
            } else {
                usage.append(" <" + parameter.getName() + ">");
            }
        });
        return usage.toString();
    }

    private void printCommands() {
        Registry.getCommandRegistry().forEach(command -> {
            Log.writeLine(command.getShortcut() + " - " + command.getDescription());
        });
        Log.writeLine("\n[required parameter] <optional parameter>");
        Log.writeLine("\nuse -[parameter] [value] to specifically set a parameter");
        Log.writeLine("\nType 'help <command>' do see detail information");
    }
}
