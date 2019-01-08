package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestCommand extends Command {

	public TestCommand() {
		super("test", "test", Arrays.asList("saas", "soos"), Arrays.asList("sees"), Arrays.asList());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map<String, String> args) {
		System.out.println(args.get("saas"));
		System.out.println(args.get("soos"));
		System.out.println(args.get("sees"));
	}

}
