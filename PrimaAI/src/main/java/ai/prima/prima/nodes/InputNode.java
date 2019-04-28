package ai.prima.prima.nodes;

import ai.prima.prima.functions.Function;
import ai.prima.prima.injector.Injector;

public abstract class InputNode extends Node{
	
	private Injector injector;
	
	public InputNode(Function function) {
		super(function);
	}
	
	protected abstract void start();

	public abstract InputNode clone();

	public void setup(Injector injector) {
		this.injector = injector;
		start();
	}
	
	protected void input(final float input){
    	this.value = input;
    	injector.register(this);
    }
}
