package ai.prima.prima.data;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ai.prima.prima.commands.Command;
import ai.prima.prima.evolution.training.Mutator;
import ai.prima.prima.evolution.training.Rating;
import ai.prima.prima.exceptions.NoRegistryEntryException;
import ai.prima.prima.exceptions.NodeGenerationException;
import ai.prima.prima.functions.Function;
import ai.prima.prima.nodes.Node;
import ai.prima.prima.training.Trainer;
import ai.prima.prima.util.Log;

public class Registry {

    private static final Map<String, Function> functions = new HashMap<>();
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, Mutator> mutators = new HashMap<>();
    private static final Map<String, Class<? extends Trainer>> trainers = new HashMap<>();
    private static final Map<String, Class<? extends Rating>> ratings = new HashMap<>();
    private static final Map<String, Class<? extends Node>> nodes = new HashMap<>();

    public static Node createNode(String name, String function) throws NodeGenerationException {
        if (functions.containsKey(function) && nodes.containsKey(name)) {
            try {
                Constructor<? extends Node> c = nodes.get(name).getDeclaredConstructor(Function.class);
                return c.newInstance(functions.get(function));
            } catch (Exception e) {
                throw new NodeGenerationException(name);
            }
        }
        return null;
    }

    public static void registerRating(Class<? extends Rating> rating) {
        String name = rating.getName();
        if (!ratings.containsKey(name)) {
            ratings.put(name, rating);
        } else {
            Log.warning("There already is a rating with the name \"" + name + "\"");
        }
    }

    public static void registerMutator(Mutator mutator) {
        String name = mutator.getClass().getName();
		if(!mutators.containsKey(name)){
		    mutators.put(name, mutator);
        } else {
            Log.warning("There already is a mutator with the name \"" + name + "\"");
        }
    }

    public static void registerNode(Class<? extends Node> node) {
        String name = node.getName();
        if (!nodes.containsKey(name)) {
            nodes.put(name, node);
        } else {
            Log.warning("There already is a node with the name \"" + name + "\"");
        }
    }

    public static void registerFunction(Function function) {
        String name = function.getClass().getName();
        if (!functions.containsKey(name)) {
            functions.put(name, function);
        } else {
            Log.warning("There already is a function with the name \"" + name + "\"");
        }
    }

    public static void registerCommand(Command command) {
        String name = command.getCmd();
        if (!commands.containsKey(name)) {
            commands.put(name, command);
        } else {
            Log.warning("There already is a command with the name \"" + name + "\"");
        }
    }

    public static void registerTrainer(Class<? extends Trainer> trainer) {
        String name = trainer.getName();
        if (!trainers.containsKey(name)) {
            trainers.put(name, trainer);
        } else {
            Log.warning("There is already a trainer with the name \"" + name + "\"");
        }
    }

    public static Function getFunction(String name) throws NoRegistryEntryException {
        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        throw new NoRegistryEntryException(name);
    }

    public static Class<? extends Rating> getRating(String name) throws NoRegistryEntryException {
        if(ratings.containsKey(name)){
            return ratings.get(name);
        }
        throw new NoRegistryEntryException(name);
    }

    public static Class<? extends Trainer> getTrainer(String name) throws NoRegistryEntryException{
        if (functions.containsKey(name)) {
            return trainers.get(name);
        }
        throw new NoRegistryEntryException(name);
    }

    public static Command getCommand(String name) throws NoRegistryEntryException {
        if (commands.containsKey(name)) {
            return commands.get(name);
        }
        throw new NoRegistryEntryException(name);
    }

    public static Mutator getMutator(String name) throws NoRegistryEntryException {
        if (mutators.containsKey(name)) {
            return mutators.get(name);
        }
        throw new NoRegistryEntryException(name);
    }

    public static Map<String, Class<? extends Node>> getNodeRegistry() {
        return nodes;
    }

    public static Map<String, Function> getFunctionRegistry() {
        return functions;
    }

    public static Map<String, Command> getCommandRegistry() {
        return commands;
    }
}
