package ai.jbon.jbon;

import java.util.List;
import java.util.Map;

import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

/**
 * Includes Input, Output and Hidden neurons to create a output
 * 
 * Includes functions to change for changing Neuron values (learning)
 * 
 * Can be loaded from a .ann file or stored into one using the NetworkLoader class
 * 
 * @author SilvanJost
 *
 */
public class Network {
	
	private Map<Long, Node> nodes;
	private List<InputNode> inputNodes;
	
	private Injector injector;
	
	public Network() {
		injector = new Injector();
	}
}
