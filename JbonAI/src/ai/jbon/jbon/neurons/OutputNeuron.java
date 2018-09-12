package ai.jbon.jbon.neurons;

import ai.jbon.jbon.nodes.OutputNode;

public class OutputNeuron extends Neuron {
	
	private OutputNode node;
	
	public OutputNeuron(OutputNode node){
		this.node = node;
	}
	
	@Override
	public void pushOutput(){
		node.execute(value);
	}
}
