package ai.jbon.jbon.commands.create;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.Network;
import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.util.Log;

public class CreateNetworkCommand extends Command{

	private static final String NAME = "name";
		
	private final JbonAI ai;
	
	public CreateNetworkCommand(JbonAI ai) {
		super("network", "Creates a new Network", 
				Arrays.asList(NAME),
				Arrays.asList(),
				Arrays.asList());
		this.ai = ai;
	}

	@Override
	public void execute(Map<String, String> args) {
		String name = args.get(NAME);
		if(name.contains(" ")) {
			Log.warning("Name contains invalid characters");
		} else {
			ai.getNetworks().add(new Network(name));
			Log.info("Created network with name " + name);
		}
	}

}
