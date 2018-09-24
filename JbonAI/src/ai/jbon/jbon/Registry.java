package ai.jbon.jbon;

import java.util.HashMap;
import java.util.Map;

import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.OutputNode;

public class Registry {

	private static Map<String, InputNode> inputNodes = new HashMap<String, InputNode>();
	
	private static Map<String, OutputNode> outputNodes = new HashMap<String, OutputNode>();
	
	public static void registerInputNode(String name, InputNode node) {
		if(inputNodes.containsKey(name)) {
			System.out.println("InputNode " + name + "was registered twice");
		}else {
			inputNodes.put(name, node);
		}
	}
	
	public static void registerOutputNode(String name, OutputNode node) {
		if(outputNodes.containsKey(name)) {
			System.out.println("OutputNode " + name + "was registered twice");
		}else {
			outputNodes.put(name, node);
		}
	}
	
}
