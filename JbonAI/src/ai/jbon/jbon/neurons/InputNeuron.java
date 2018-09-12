package ai.jbon.jbon.neurons;

public class InputNeuron extends Neuron {     
    
    public void injectInput(float input){
    	this.value = input;
    	this.pushOutput();
    }

}
