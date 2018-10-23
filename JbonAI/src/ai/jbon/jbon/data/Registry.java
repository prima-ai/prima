package ai.jbon.jbon.data;

import java.util.HashMap;
import java.util.Map;

import ai.jbon.jbon.Log;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

public class Registry {

	private static Map<String, Node> nodes = new HashMap<String, Node>();
	
	private static Map<String, Function> functions = new HashMap<String, Function>();
	
	public static Function getFunction(String name) {
		return functions.get(name);
	}
	
	public static void registerNode(Node node) {
		if(nodes.containsKey(node.getTag())) {
			Log.warning("Node " + node.getTag()  + "was registered twice");
		} else {
			nodes.put(node.getTag(), node);
		}
	}
	
	public static void registerFunciton(String name, Function function) {
		if(functions.containsKey(name)) {
			Log.warning("Function " + name + "was registered twice");
		}else {
			functions.put(name, function);
		}
	}
	
	public static Node generateNode(String name, Function function) {
		return (Node) nodes.get(name).generate(function);
	}
}
