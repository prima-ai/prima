package ai.jbon.jbon.example.outputNodes;

import ai.jbon.jbon.nodes.OutputNode;

public class ConsoleOutputNode extends OutputNode{

	@Override
	public void execute(float value) {
		System.out.println("Output: " + value);
	}

}
