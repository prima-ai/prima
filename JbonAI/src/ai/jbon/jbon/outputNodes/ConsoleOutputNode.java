package ai.jbon.jbon.outputNodes;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

public class ConsoleOutputNode extends OutputNode{
	
	public ConsoleOutputNode(Function function) {
		super("ConsoleOutputNode", function);
	}

	@Override
	public void execute(float value) {
		System.out.println("Output: " + value);
	}

	@Override
	protected Node createInstance(Function function) {
		return new ConsoleOutputNode(function);
	}

}
