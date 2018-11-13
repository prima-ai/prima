package ai.jbon.jbon.exceptions;

public class NodeGenerationException extends Exception{

	public NodeGenerationException(String name) {
		super("Failed to generate Node \"" + name + "\"");	
	}
}
