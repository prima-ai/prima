package ai.jbon.jbon.commands;

import java.util.List;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Log;

public class EchoCommand extends Command{

	private static final int MESSAGE = 0;
	
	public EchoCommand() {
		super("echo <Message>", 
				"Prints the given text to the console");
	}

	@Override
	public void execute(List<String> args) {
		Log.info(args.get(MESSAGE));
	}

}
