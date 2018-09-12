package ai.jbon.jbon.nodes;

import ai.jbon.jbon.neurons.InputNeuron;
import ai.jbon.jbon.neurons.OutputNeuron;

public abstract class InputNode {

	private InputNeuron neuron;
	
	protected abstract float getInputValue();
	
	public void input(){
		float value = getInputValue();
		neuron.injectInput(value);
	}
}
