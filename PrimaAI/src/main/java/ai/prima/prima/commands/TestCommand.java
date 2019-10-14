package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

public class TestCommand extends Command {

	public TestCommand() {
		super("test", "test");
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		System.out.println("test");
	}

}
