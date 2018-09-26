package ai.jbon.jbon;

import ai.jbon.jbon.nodes.Node;

public class Connection {

    private Node target;
    private float weight;
    private float value;
    
    public Connection(final Node target) {
        this.target = target;
        weight = (float) Math.random()*2-1;
    }
    
    public Connection(final Node target, final float w) {
        this.target = target;
        weight = w;
    }
    
    // Triggers the neuron target calculate its output and push it
    public void push(){
    	target.pushOutput();
    }
    
    // Injects a value intarget the target node valuebuffer
    public void injectValue(final float value) {
    	this.value = value * this.weight;
    	target.injectValue(this.value);
    }
    
    // Changing the weight of the connection
    public void adjustWeight(float deltaWeight) {
        weight += deltaWeight;
    }

    public Node getTarget() {
    	return this.target;
    }
    
    public float getValue(){
    	return value;
    }
    
    public float getWeight() {
        return weight;
    }    
}
