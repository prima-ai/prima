package ai.jbon.jbon.exceptions;

public class RegistryFailedException extends Exception{

	public RegistryFailedException() {
		super("Class could not be registered.");
	}
	
	public RegistryFailedException(String msg) {
		super(msg);
	}
	
	public RegistryFailedException(Class c, String name) {
		super("Class " + c.getName() + " could not be registered with the name " + name + ".");
	}
}
