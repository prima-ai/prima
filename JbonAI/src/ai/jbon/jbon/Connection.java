package ai.jbon.jbon;

import ai.jbon.jbon.neurons.Neuron;

public class Connection {

    private Neuron from;     // Connection goes from. . .
    private Neuron to;       // To. . .
    private float weight;   // Weight of the connection. . .
    private float value;

    // Constructor  builds a connection with a random weight
    public Connection(Neuron a_, Neuron b_) {
    	
        from = a_;
        to = b_;
        weight = (float) Math.random()*2-1;
    }
    
    // In case I want to set the weights manually, using this for testing
    public Connection(Neuron a_, Neuron b_, float w) {
    	
        from = a_;
        to = b_;
        weight = w;
    }

    // Refreshes the value in the pipeline and triggers the end neuron
    public void push(float value){
    	
    	this.value = value;
    	to.calcOutput();
    	to.pushOutput();
    	
    }
    
    // Changing the weight of the connection
    public void adjustWeight(float deltaWeight) {
    	
        weight += deltaWeight;
        
    }

    // Returns the end value with weight adjustment
    public float getValue(){
    	
    	return value;
    	
    }
    
    public Neuron getFrom() {
        return from;
    }
    
    public Neuron getTo() {
        return to;
    }  
    
    public float getWeight() {
        return weight;
    }    
}
