package ai.prima.prima.exceptions;

import ai.prima.prima.commands.Command;

public class CommandSyntaxException extends Exception {

	public CommandSyntaxException(Command command) {
		super("Invalid syntax for command " + command.getShortcut());
	}
}
