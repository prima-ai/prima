package ai.jbon.jbon.plugin;

import ai.jbon.jbon.JbonAI;
import ai.jbon.jbon.data.Registry;

public abstract class Plugin {

	private final String name;
	private final String version;
	private final String description;
	
	public Plugin(final String name, final String version, final String description) {
		this.name = name;
		this.version = version;
		this.description = description;
	}
	
	public abstract void registerNodes(Registry registry);
	
	public abstract void registerFunctions(Registry registry);
	
	public abstract void registerCommands(Registry registry);
	
	public abstract void init(JbonAI ai);
	
	public abstract void unload();
	
	public abstract void run();
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getVersion() {
		return this.version;
	}
}
