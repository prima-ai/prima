package ai.prima.prima.inputNodes;

import ai.prima.prima.functions.Function;
import ai.prima.prima.nodes.InputNode;

public class PositiveInputNode extends InputNode{

	public PositiveInputNode(Function function) {
		super(function);
	}

	@Override
	protected void start() {
		input(1);
	}

	@Override
	public PositiveInputNode clone() {
		return new PositiveInputNode(function);
	}
}
