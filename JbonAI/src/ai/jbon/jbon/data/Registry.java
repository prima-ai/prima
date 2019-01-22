package ai.jbon.jbon.data;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.exceptions.NoRegistryEntryException;
import ai.jbon.jbon.exceptions.NodeGenerationException;
import ai.jbon.jbon.exceptions.RegistryFailedException;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.Node;

public class Registry {

	private static final Map<String, Class<? extends Node>> nodes = new HashMap<String, Class<? extends Node>>();
	private static final Map<String, Function> functions = new HashMap<String, Function>();
	private static final Map<String, Command> commands = new HashMap<String, Command>();
	
	public static Node createNode(String name, String function) throws NodeGenerationException {
		if(functions.containsKey(function) && nodes.containsKey(name)) {
			try {
				Constructor<? extends Node> c = nodes.get(name).getDeclaredConstructor(Function.class);
				return c.newInstance(functions.get(function));
			} catch(Exception e) {
				throw new NodeGenerationException(name);
			}
		}
		return null;
	}
	
	public static Function getFunction(String name) {
		if(functions.containsKey(name)) {
			return functions.get(name);
		}
		return null;
	}
	
	public static Command getCommand(String name) {
		if(commands.containsKey(name)) {
			return commands.get(name);
		}
		return null;
	}
	
	public static void registerNode(Class<? extends Node> node) {
		if(!nodes.containsKey(node.getName())) {
			nodes.put(node.getName(), node);
		} else {
			new RegistryFailedException(node, node.getName()).printStackTrace();
		}
	}
	
	public static void registerFunction(Function function) throws RegistryFailedException {
		if(!functions.containsKey(function.getClass().getName())) {
			functions.put(function.getClass().getName(), function);
		} else {
			new RegistryFailedException(function.getClass(), function.getClass().getName()).printStackTrace();
		}
	}
	
	public static void registerCommand(Command command) throws RegistryFailedException {
		if(!commands.containsKey(command.getClass().getName())) {
			commands.put(command.getCmd(), command);
		} else {
			new RegistryFailedException(command.getClass(), command.getCmd()).printStackTrace();
		}
	}

	public static Map<String, Class<? extends Node>> getNodes() { return nodes; }

	public static Map<String, Function> getFunctions() { return functions; }

	public static Map<String, Command> getCommands() { return commands; }
}
