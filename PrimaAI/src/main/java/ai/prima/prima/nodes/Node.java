package ai.prima.prima.nodes;

import java.util.ArrayList;
import java.util.List;

import ai.prima.prima.functions.Function;

public class Node {
		
	protected float value;
	private float bias;
	protected Function function;
	protected List<Connection> connections;
	private List<Float> valueBuffer;

	public Node(){
		valueBuffer = new ArrayList<>();
		valueBuffer.add(bias);
	}

	public Node(Function function) {
		this.connections = new ArrayList<>();
		this.function = function;
		valueBuffer = new ArrayList<>();
		valueBuffer.add(bias);
	}

	public Node(Function function, List<Connection> connections) {
		this.connections = connections;
		this.function = function;
		valueBuffer = new ArrayList<>();
		valueBuffer.add(bias);
	}
	
	// Calculate value of node
	public void calcOutput() {
		this.value = function.apply(valueBuffer);
		valueBuffer.clear();
		valueBuffer.add(bias);
	}
	
	public List<Connection> pushOutput() {
		connections.forEach(connection -> {
			connection.injectValue(value);
		});
		return connections;
	}

	public Node clone() {
		return new Node(function);
	}

	public void injectValue(final float value) {
		this.valueBuffer.add(value);
	}

	public float getValue() { return value; }
	
	public Function getFunction() { return function; }
	
	public List<Connection> getConnections() { return this.connections; }
}
