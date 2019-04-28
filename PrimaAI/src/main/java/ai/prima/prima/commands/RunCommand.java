package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.NetworkImage;
import ai.prima.prima.util.Log;

public class RunCommand extends Command{

	private static final String IMAGE = "image";
	
	private final PrimaAI ai;
	
	public RunCommand(PrimaAI ai) {
		super("run", "Starts a network image",
				Arrays.asList(),
				Arrays.asList(IMAGE),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		if(args.containsKey(IMAGE)) {
			String name = args.get(IMAGE);
			findImage(name).start();
			Log.info("Started image " + name);
		} else {
			ai.getSelectedImage().start();
		}
	}
	
	private NetworkImage findImage(String term) {
		return ai.getNetworkImages().stream()
				.filter(image -> image.getName().equals(term)).findAny().get();
	}
}
