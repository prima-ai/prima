package ai.jbon.jbon.exceptions;

import ai.jbon.jbon.commands.Command;

public class CommandSyntaxException extends Exception {

	public CommandSyntaxException(Command command) {
		super("Invalid syntax for command " + command.getCmd());
	}
}
