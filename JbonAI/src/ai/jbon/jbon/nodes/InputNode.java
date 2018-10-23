package ai.jbon.jbon.nodes;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.injector.Injector;

public abstract class InputNode extends Node{
	
	private Injector injector;
	
	public InputNode(String tag, Function function) {
		super(tag, function);
	}
	
	protected abstract void start();
	
	protected abstract Node createInstance(Function function);
	
	@Override
	public Node generate(Function function) {
		return createInstance(function);
	}
	
	public void setup(Injector injector) {
		this.injector = injector;
		start();
	};
	
	protected void input(final float input){
    	this.value = input;
    	injector.register(this);
    }
}
