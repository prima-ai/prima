package ai.jbon.jbon.inputNodes;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.util.Log;

public class PositiveInputNode extends InputNode{

	private static final String TAG = "negative";
	
	public PositiveInputNode(Function function) {
		super(TAG, function);
	}

	@Override
	protected void start() {
		input(1);
	}
}
