package ai.prima.prima.plugins;

import ai.prima.prima.PrimaAI;

public abstract class Plugin {

	private final String name;
	private final String version;
	private final String description;
	private final String author;
	private final String website;
	
	public Plugin(final String name, final String version, final String description, final String author, final String website) {
		this.name = name;
		this.version = version;
		this.description = description;
		this.author = author;
		this.website = website;
	}

	public abstract void init(PrimaAI ai);

	public abstract void registerNodes();
	
	public abstract void registerFunctions();
	
	public abstract void registerCommands();

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

	public String getAuthor() { return author; }

	public String getWebsite() { return website; }
}
