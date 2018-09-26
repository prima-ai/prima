package ai.jbon.jbon.nodes;

import java.util.ArrayList;
import java.util.List;

import ai.jbon.jbon.Connection;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.functions.SigmoidFunction;

public class Node {

	protected float value;
	private Function function;
	protected List<Connection> connections;
	private List<Float> valueBuffer;
	
	public Node(final List<Connection> connections) {
		this.connections = connections;
		valueBuffer = new ArrayList<Float>();
	}
	
	public Node() {
		this.connections = new ArrayList<Connection>();
		valueBuffer = new ArrayList<Float>();
		function = new SigmoidFunction();
	}
	
	// Calculate value of node
	public void calcOutput() {
		this.value = function.getOutput(valueBuffer);
		valueBuffer.clear();
	}
	
	public List<Connection> pushOutput() {
		connections.forEach(connection -> {
			connection.injectValue(value);
		});
		return connections;
	}
	
	// Stores the value in the valuebuffer
	public void injectValue(final float value) {
		this.valueBuffer.add(value);
	}
	
	public void addConnection(final Connection c) {
		connections.add(c);
	}

	public float getValue() {
		return value;
	}
}
