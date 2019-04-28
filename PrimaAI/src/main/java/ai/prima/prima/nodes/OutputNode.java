package ai.prima.prima.nodes;

import java.util.List;

import ai.prima.prima.functions.Function;

public abstract class OutputNode extends Node{
	
	public abstract void execute(float value);

	public abstract OutputNode clone();

	public OutputNode(Function function) {
		super(function);
	}
	
	@Override
	public List<Connection> pushOutput(){
		execute(value);
		connections.forEach(connection -> {
			connection.injectValue(value);
		});
		return connections;
	}
}
