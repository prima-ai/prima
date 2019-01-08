package ai.jbon.jbon.nodes;

import java.util.ArrayList;
import java.util.List;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.functions.IdentityFunction;

public class Node {
		
	protected float value;
	private float bias;
	private Function function;
	protected List<Connection> connections;
	private List<Float> valueBuffer;
	
	public Node(String tag, Function function) {
		this.connections = new ArrayList<Connection>();
		this.function = function;
		valueBuffer = new ArrayList<Float>();
		valueBuffer.add(bias);
		function = new IdentityFunction();
	}
	
	public Node(Function function) {
		this.connections = new ArrayList<Connection>();
		this.function = function;
		valueBuffer = new ArrayList<Float>();
		valueBuffer.add(bias);
		function = new IdentityFunction();
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
	
	public void injectValue(final float value) {
		this.valueBuffer.add(value);
	}
	
	public void addConnection(final Connection c) {
		connections.add(c);
	}
	
	public float getValue() { return value; }
	
	public Function getFunction() { return function; }
	
	public List<Connection> getConnections() { return this.connections; }
}
