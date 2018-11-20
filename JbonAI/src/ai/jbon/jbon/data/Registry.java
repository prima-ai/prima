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
	
	public Node createNode(String name, String function) throws NodeGenerationException {
		try {
			Constructor<? extends Node> c = nodes.get(name).getDeclaredConstructor(Function.class);
			return c.newInstance(functions.get(function));
		} catch(Exception e) {
			throw new NodeGenerationException(name);
		}
	}
	
	public Function getFunction(String name) throws NoRegistryEntryException {
		if(functions.containsKey(name)) {
			return functions.get(name);
		} else {
			throw new NoRegistryEntryException(name);
		}
	}
	
	public Command getCommand(String name) throws NoRegistryEntryException {
		if(commands.containsKey(name)) {
			return commands.get(name);
		} else {
			throw new NoRegistryEntryException(name);
		}
	}
	
	public void registerNode(Class<? extends Node> node) throws RegistryFailedException {
		if(!nodes.containsKey(node.getName())) {
			nodes.put(node.getName(), node);
		} else {
			throw new RegistryFailedException(node, node.getName());
		}
	}
	
	public void registerFunction(Function function) throws RegistryFailedException {
		if(!functions.containsKey(function.getClass().getName())) {
			functions.put(function.getClass().getName(), function);
		} else {
			throw new RegistryFailedException(function.getClass(), function.getClass().getName());
		}
	}
	
	public void registerCommand(Command command) throws RegistryFailedException {
		if(!commands.containsKey(command.getClass().getName())) {
			commands.put(command.getCmd(), command);
		} else {
			throw new RegistryFailedException(command.getClass(), command.getCmd());
		}
	}
}
