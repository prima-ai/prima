package ai.jbon.jbon.nodes;

import java.util.List;

import ai.jbon.jbon.Connection;

public abstract class OutputNode extends Node{
	
	public abstract void execute(float value);
	
	@Override
	public List<Connection> pushOutput(){
		this.execute(value);
		return null;
	}
}
