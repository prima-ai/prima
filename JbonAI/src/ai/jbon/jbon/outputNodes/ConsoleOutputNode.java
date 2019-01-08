package ai.jbon.jbon.outputNodes;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

public class ConsoleOutputNode extends OutputNode{
	
	private static final String TAG = "consolein";
	
	public ConsoleOutputNode(Function function) {
		super(TAG, function);
	}

	@Override
	public void execute(float value) {
		System.out.println("Output: " + value);
	}
}
