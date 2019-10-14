package ai.prima.prima.commands;

import java.util.Arrays;
import java.util.Map;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.NetworkImage;
import ai.prima.prima.util.Log;

public class RunCommand extends Command{

	private static final Parameter IMAGE_PARAMETER = new Parameter("image", Parameter.Requirement.OPTIONAL);
	
	private final PrimaAI ai;
	
	public RunCommand(PrimaAI ai) {
		super("run", "Starts a network image", Arrays.asList(IMAGE_PARAMETER));
		this.ai = ai;
	}

	@Override
	public void execute(Map<Parameter, String> values) {
		ai.getPrompt().stop();
		if(values.containsKey(IMAGE_PARAMETER)) {
			String name = values.get(IMAGE_PARAMETER);
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
