package ai.jbon.jbon.exceptions;

public class NoRegistryEntryException extends Exception {
	
	public NoRegistryEntryException(String name) {
		super("No entry with name \"" + name + "\"");
	}
}
