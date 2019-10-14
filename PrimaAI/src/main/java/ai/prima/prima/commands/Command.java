package ai.prima.prima.commands;

import ai.prima.prima.util.Log;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Command {

    private String shortcut;
    private String description;
    private List<Parameter> usage = new ArrayList<>();
    private List<Command> subCommands = new ArrayList<>();

    public Command(String shortcut, String description) {
        this.shortcut = shortcut;
        this.description = description;
    }

    public Command(String shortcut, String description, List<Parameter> parameters) {
        this.shortcut = shortcut;
        this.description = description;
        parameters.sort(new ParameterComparator());
        this.usage = parameters;
    }

    public Command(String shortcut, List<Command> subCommands, String description) {
        this.shortcut = shortcut;
        this.description = description;
        this.subCommands = subCommands;
    }

    public Command(String shortcut, String description, List<Parameter> parameters, List<Command> subCommands) {
        this.shortcut = shortcut;
        this.description = description;
        parameters.sort(new ParameterComparator());
        this.usage = parameters;
        this.subCommands = subCommands;
    }

    public void run(List<String> arguments) {
        if (!arguments.isEmpty()) {
            Optional<Command> subCommand = findSubCommand(arguments.get(0));
            if (subCommand.isPresent()) {
                arguments.remove(0);
                subCommand.get().run(arguments);
                return;
            }
        }
        Map<Parameter, String> parameterValues = new HashMap<>();
        assignMarkedParameters(arguments, parameterValues);
        assignDefaultParameters(arguments, parameterValues);
        execute(parameterValues);
    }

    //TODO actual error feedback

    private void assignDefaultParameters(List<String> arguments, Map<Parameter, String> parameterValues) {
        for (int i = 0; i < arguments.size(); i++) {
            parameterValues.put(usage.get(i), arguments.get(i));
        }
        if (!parameterValues.keySet().containsAll(usage.stream()
                .filter(parameter -> parameter.getRequirement() == Parameter.Requirement.REQUIRED)
                .collect(Collectors.toList()))) {
            Log.error("Missing parameters");
        }
    }

    private void assignMarkedParameters(List<String> arguments, Map<Parameter, String> parameterValues) {
        for (int i = 0; i < arguments.size(); i++) {
            String argument = arguments.get(i);
            if (argument.startsWith("-")) {
                Optional<Parameter> parameter = findParameter(argument);
                if (parameter.isPresent() && i < arguments.size()) {
                    parameterValues.put(parameter.get(), arguments.get(i + 1));
                    arguments.remove(i);
                    arguments.remove(i+1);
                    i += 2;
                }
            }
        }
    }

    private Optional<Parameter> findParameter(String term) {
        return usage.stream().filter(parameter -> parameter.getName().equals(term)).findFirst();
    }

    public abstract void execute(Map<Parameter, String> values);

    private Optional<Command> findSubCommand(String shortcutArgument) {
        return subCommands.stream()
                .filter(subCommand -> subCommand.shortcut.equals(shortcutArgument))
                .findAny();
    }

    public String getShortcut() {
        return shortcut;
    }

    public List<Command> getSubCommands() {
        return subCommands;
    }

    public String getDescription() {
        return description;
    }

    public List<Parameter> getUsage() {
        return usage;
    }
}
