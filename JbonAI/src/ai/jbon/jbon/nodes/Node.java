package ai.jbon.jbon.neurons;

import java.util.ArrayList;
import java.util.List;

import ai.jbon.jbon.Connection;
import ai.jbon.jbon.functions.Function;

public class Neuron {
	
	private ArrayList<Connection> connections;
	
	protected float value;
	
	private Function function;
	
	// regular neuron
	public Neuron() {
		
		connections = new ArrayList();
	}
	
	
	// calculate output of neuron
	public void calcOutput() {
		
		
		List<Float> values = new ArrayList<Float>();
		
		for(Connection connection : connections){
			
			values.add(connection.getValue() * connection.getWeight());
			
		}
		
		this.value = function.getOutput(values);
		
	}
	
	// Pushes the momentary value to all outgoing connections
	public void pushOutput(){
		
		for(Connection conn : connections){
			
			conn.push(this.value);
			
		}
		
	}
	
	
	public void addConnection(Connection c) {
		connections.add(c);
	}

	
	public ArrayList getConnections() {
		return connections;
	}
}
