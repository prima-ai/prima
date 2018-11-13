package ai.jbon.jbon.nodes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import ai.jbon.jbon.Connection;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.functions.IdentityFunction;

public class Node {
	
	private final String tag;
	
	protected float value;
	private float bias;
	private Function function;
	protected List<Connection> connections;
	private List<Float> valueBuffer;
	
	public Node(String tag, Function function) {
		this.tag = tag;
		this.connections = new ArrayList<Connection>();
		this.function = function;
		valueBuffer = new ArrayList<Float>();
		valueBuffer.add(bias);
		function = new IdentityFunction();
	}
	
	public Node(Function function) {
		this.tag = "Node";
		this.connections = new ArrayList<Connection>();
		this.function = function;
		valueBuffer = new ArrayList<Float>();
		valueBuffer.add(bias);
		function = new IdentityFunction();
	}
	
	// Calculate value of node
	public void calcOutput() {
		this.value = function.getOutput(valueBuffer);
		valueBuffer.clear();
		valueBuffer.add(bias);
	}
	
	public List<Connection> pushOutput() {
		connections.forEach(connection -> {
			connection.injectValue(value);
		});
		return connections;
	}
	
	public Node generate(Function function) {
		return new Node(tag, function);
	}
	
	public void injectValue(final float value) {
		this.valueBuffer.add(value);
	}
	
	public void addConnection(final Connection c) {
		connections.add(c);
	}

	public String getTag() {
		return this.tag;
	}
	
	public float getValue() {
		return value;
	}
	
	public List<Connection> getConnections(){
		return this.connections;
	}
}
