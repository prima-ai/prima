package ai.prima.prima.nodes;

public class Connection {

    private Node target;
    private float weight;
    private float value;

    public Connection() {
        weight = (float) Math.random()*2-1;
    }

    public Connection(final Node target) {
        this.target = target;
        weight = (float) Math.random()*2-1;
    }
    
    public Connection(final Node target, final float w) {
        this.target = target;
        weight = w;
    }

    public void injectValue(final float value) {
    	this.value = value * this.weight;
    	target.injectValue(this.value);
    }

    public void adjustWeight(float deltaWeight) {
        weight += deltaWeight;
    }

    public Node getTarget() {
    	return this.target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public float getValue(){
    	return value;
    }
    
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
