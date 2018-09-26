package ai.jbon.jbon.nodes;

import ai.jbon.jbon.Injector;

public abstract class InputNode extends Node{
	
	private Injector injector;
	
	public InputNode(Injector injector) {
		this.injector = injector;
	}
	
	public abstract void setup();
	
	protected void input(final float input){
    	this.value = input;
    	injector.register(this);
    }
}
