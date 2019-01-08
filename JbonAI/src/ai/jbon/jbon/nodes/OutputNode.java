package ai.jbon.jbon.nodes;

import java.util.List;

import ai.jbon.jbon.functions.Function;

public abstract class OutputNode extends Node{
	
	public static final String TAG = "OutputNode";
	
	public abstract void execute(float value);
	
	public OutputNode(String tag, Function function) {
		super(tag, function);
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
