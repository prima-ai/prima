package ai.jbon.jbon.neurons;

import java.util.ArrayList;

public class Neuron {
	private float output;
	private ArrayList<E> connections;
	private boolean bias = false;
	
	// regular neuron
	public Neuron() {
		output = 0;
		
		connections = new ArrayList();
		bias = false;
	}
	
	// constructor for bias neuron
	public Neuron(int i) {
		output = i;
		connections = new ArrayList();
		bias = true;
	}
	
	// calculate output of neuron
	public void calcOutput() {
		if(bias) {
			// nothing
		} else {
			float sum = 0;
			float bias = 0;
			for(int i = 0; i<connections.size(); i++) {
				Connection c = (Connection) connections.get(i);
				Neuron from = c.getFrom();
				Neuron to = c.getTo();
				
				if(to==this) {
					if(from.bias) {
						bias = from.getOutput()*c.getWeight();
					} else {
						sum += from.getOutput()*c.getWeight();
					}
				}
			}
			output = f(bias+sum);
		}
	}
	
	void addConnection(Connection c) {
		connections.add(c);
	}
	
	float getOutput() {
		return output;
	}
	
	// sigmond function
	public static float f(float x) {
		return 1.0f/(1.0f+(float)Math.exp(-x));
	}
	
	public ArrayList getConnections() {
		return connections;
	}
}
