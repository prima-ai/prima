package ai.jbon.jbon.data;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ai.jbon.jbon.exceptions.NodeGenerationException;
import ai.jbon.jbon.exceptions.RegistryFailedException;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.Node;

public class Registry {

	private static Map<String, Class<? extends Node>> nodes = new HashMap<String, Class<? extends Node>>();
	private static Map<String, Function> functions = new HashMap<String, Function>();
	
	public Node createNode(String name, String function) throws NodeGenerationException{
		Node node;
		try {
			Constructor<? extends Node> c = nodes.get(name).getDeclaredConstructor(Function.class);
			node = c.newInstance(functions.get(function));
		} catch(Exception e) {
			throw new NodeGenerationException(name);
		}
		return node;
	}
	
	public void registerFunction(Class<? extends Function> function) throws RegistryFailedException {
		try {
			functions.put(function.getName(), createFunction(function));
		} catch(Exception e) {
			throw new RegistryFailedException(function, function.getName());
		}
	}
	
	public void registerNode(Class<? extends Node> node) throws RegistryFailedException {
		try {
			nodes.put(node.getName(), node);
		} catch(Exception e) {
			throw new RegistryFailedException(node, node.getName());
		}
	}
	
	private Function createFunction(Class<? extends Function> function) throws InstantiationException, IllegalAccessException {
		return function.newInstance();
	}
}
