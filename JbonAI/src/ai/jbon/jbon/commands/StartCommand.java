package ai.jbon.jbon.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.NetworkImage;
import ai.jbon.jbon.util.Log;

public class StartCommand extends Command{

	private static final String IMAGE = "image";
	
	private final JbonAI ai;
	
	public StartCommand(JbonAI ai) {
		super("start", "Starts a network image",
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
