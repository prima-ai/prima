package ai.jbon.jbon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import ai.jbon.jbon.commands.BuildCommand;
import ai.jbon.jbon.commands.DuplicateCommand;
import ai.jbon.jbon.commands.EchoCommand;
import ai.jbon.jbon.commands.ExitCommand;
import ai.jbon.jbon.commands.FunctionCommand;
import ai.jbon.jbon.commands.HelpCommand;
import ai.jbon.jbon.commands.NetworkCommand;
import ai.jbon.jbon.commands.SaveCommand;
import ai.jbon.jbon.commands.UseCommand;
import ai.jbon.jbon.commands.StartCommand;
import ai.jbon.jbon.commands.TestCommand;
import ai.jbon.jbon.commands.create.CreateCommand;
import ai.jbon.jbon.commands.delete.DeleteCommand;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.data.ResourceLoader;
import ai.jbon.jbon.exceptions.RegistryFailedException;
import ai.jbon.jbon.functions.IdentityFunction;
import ai.jbon.jbon.inputNodes.ConsoleInputNode;
import ai.jbon.jbon.inputNodes.PositiveInputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.outputNodes.ConsoleOutputNode;
import ai.jbon.jbon.plugin.Plugin;
import ai.jbon.jbon.util.Log;
import ai.jbon.jbon.data.ClassLoader;

public class JbonAI {

	private static final String BRAND = "      _ _                    _    ___ \r\n" + 
										"     | | |__   ___  _ __    / \\  |_ _|\r\n" + 
										"  _  | | '_ \\ / _ \\| '_ \\  / _ \\  | | \r\n" + 
										" | |_| | |_) | (_) | | | |/ ___ \\ | | \r\n" + 
										"  \\___/|_.__/ \\___/|_| |_/_/   \\_\\___|\r\n" + 
										"--------------------------------------";
	
	private static final File PLUGIN_DIR = new File("../plugins");
	public static final File AI_DIR = new File(System.getProperty("user.home") + "\\jbonai");
	public static final File NETWORK_DIR = new File(System.getProperty("user.home") + "\\jbonai\\networks");

	public static final String NETWORK_EXTENSION = "network";
	
	private final Registry registry = new Registry();
	private final ResourceLoader resourceLoader = new ResourceLoader(registry);
	private final ClassLoader classLoader = new ClassLoader();
	private final List<Network> networks = new ArrayList<>();
	private final List<NetworkImage> images = new ArrayList<>();
	private final List<Plugin> plugins = new ArrayList<>();
	private final Prompt prompt = new Prompt(registry);
	
	private Network selectedNetwork;
	private NetworkImage selectedImage;
	
	public JbonAI() {
		Log.writeLine(BRAND);
		try {
			registry.registerCommand(new EchoCommand());
			registry.registerCommand(new TestCommand());
			registry.registerCommand(new HelpCommand(registry));
			registry.registerCommand(new CreateCommand(this));
			registry.registerCommand(new NetworkCommand(this));
			registry.registerCommand(new UseCommand(this));
			registry.registerCommand(new BuildCommand(this));
			registry.registerCommand(new StartCommand(this));
			registry.registerCommand(new SaveCommand(this));
			registry.registerCommand(new DeleteCommand(this));
			registry.registerCommand(new ExitCommand(this));
			registry.registerCommand(new FunctionCommand(this));
			registry.registerCommand(new DuplicateCommand(this));
			registry.registerNode(ConsoleInputNode.class);
			registry.registerNode(ConsoleOutputNode.class);
			registry.registerNode(PositiveInputNode.class);
			registry.registerNode(Node.class);
			registry.registerFunction(new IdentityFunction());
		} catch (RegistryFailedException e) {
			e.printStackTrace();
		}
		loadPlugins();
		initPlugins();
		initFunctions();
		initNodes();
		initCommands();
		loadNetworks();
		runPlugins();
		unloadPlugins();
	}

	public static void main(String args[]) {
		JbonAI ai = new JbonAI();
		if (!PLUGIN_DIR.exists()) {
			PLUGIN_DIR.mkdir();
		}
		if(!AI_DIR.exists()) {
			AI_DIR.mkdir();
		}
		if(!NETWORK_DIR.exists()) {
			NETWORK_DIR.mkdir();
		}
		ai.loadPlugins();		
		ai.run();
	}

	public void run() {
		prompt.run();
	}

	public void loadPlugins() {
		List<File> pluginJars = resourceLoader.loadPluginFiles(PLUGIN_DIR);
		pluginJars.forEach(jar -> {
			List<Class<?>> classes = classLoader.loadClassesFromJar(jar);
			findPluginClasses(classes).forEach(c -> {
				addPluginClass((Class<? extends Plugin>) c);
			});
		});
	}
	
	public void select(Network network) {
		this.selectedImage = null;
		this.selectedNetwork = network;
	}
	
	public void select(NetworkImage image) {
		this.selectedNetwork = null;
		this.selectedImage = image;
	}

	public void unselect() {
		this.selectedNetwork = null;
		this.selectedImage = null;
	}

	private void loadNetworks() {
		List<File> files = resourceLoader.getAllFilesFromDir(NETWORK_DIR);
		files.stream()
			.filter(file -> file.getName().endsWith("." + NETWORK_EXTENSION))
			.forEach(file -> {
				loadNetwork(file);
			});
	}
	
	private void loadNetwork(File file) {
		try {
			Network network = resourceLoader.loadNetwork(file);
			networks.add(network);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void addPluginClass(Class<? extends Plugin> c) {
		try {
			plugins.add(c.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private List<Class<? extends Plugin>> findPluginClasses(List<Class<?>> classes) {
		List<Class<? extends Plugin>> pluginClasses = new ArrayList<>();
		classes.stream().filter(c -> Plugin.class.isAssignableFrom(c)).forEach(c -> {
			pluginClasses.add((Class<? extends Plugin>) c);
		});
		return pluginClasses;
	}

	private void initPlugins() {
		plugins.forEach(plugin -> {
			plugin.init(this);
		});
	}

	private void initCommands() {
		plugins.forEach(plugin -> {
			plugin.registerCommands(registry);
		});
	}

	private void initFunctions() {
		plugins.forEach(plugin -> {
			plugin.registerFunctions(registry);
		});
	}

	private void initNodes() {
		plugins.forEach(plugin -> {
			plugin.registerNodes(registry);
		});
	}

	private void runPlugins() {
		plugins.forEach(plugin -> {
			plugin.run();
		});
	}

	private void unloadPlugins() {
		plugins.forEach(plugin -> {
			plugin.unload();
		});
	}
	
	public void exit() {
		Log.info("Stopping JbonAI");
		Log.info("Saving Networks");
		networks.forEach(network -> {
			try {
				resourceLoader.storeNetwork(network);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		Log.info("Cya");
		System.exit(0);
	}
	
	public List<NetworkImage> getNetworkImages() { return images; }

	public List<Network> getNetworks() { return networks; }
	
	public Prompt getPrompt() { return prompt; }
	
	public Network getSelectedNetwork() { return selectedNetwork; }
	
	public NetworkImage getSelectedImage() { return selectedImage; }
	
	public Registry getRegistry() { return registry; }
	
	public ResourceLoader getResourceLoader() { return resourceLoader; }
}
