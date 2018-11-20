package ai.jbon.jbon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.commands.EchoCommand;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.data.ResourceLoader;
import ai.jbon.jbon.exceptions.LoadClassFromFileFailedException;
import ai.jbon.jbon.exceptions.NoRegistryEntryException;
import ai.jbon.jbon.exceptions.RegistryFailedException;
import ai.jbon.jbon.functions.IdentityFunction;
import ai.jbon.jbon.functions.SigmoidFunction;
import ai.jbon.jbon.inputNodes.ConsoleInputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.outputNodes.ConsoleOutputNode;
import ai.jbon.jbon.plugin.Plugin;
import ai.jbon.jbon.data.ClassLoader;

public class JbonAI {

	private static final File PLUGIN_DIR = new File("../plugins");
	
	private final Registry registry = new Registry();
	private final ResourceLoader resourceLoader = new ResourceLoader();
	private final ClassLoader classLoader = new ClassLoader();
	private final List<NetworkThread> networkThreads = new ArrayList<>();
	private final List<Plugin> plugins = new ArrayList<>();
	private final Scanner scanner = new Scanner(System.in);
	
	private boolean running;
	
	public JbonAI() {
		initPlugins();
		initFunctions();
		initNodes();
		initCommands();
		runPlugins();
		unloadPlugins();
	}
	
	public static void main(String args[]) {
		JbonAI ai = new JbonAI();
		
		if(!PLUGIN_DIR.exists()) {
			PLUGIN_DIR.mkdir();
		}
		
		ai.loadPlugins();
		
		/*
		Function defaultFunc = Registry.getFunction("identity");
		InputNode input = (InputNode) Registry.generateNode("ConsoleInputNode", defaultFunc);
		Node node = Registry.generateNode("Node", defaultFunc);
		OutputNode output = (OutputNode) Registry.generateNode("ConsoleOutputNode", defaultFunc);
		
		node.addConnection(new Connection(output));
		input.addConnection(new Connection(node));
		
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(input);
		nodes.add(node);
		nodes.add(output);
		Network network = new Network(nodes);
		*/
		
		/*try {
			loader.storeNetwork(network, new File("C:\\Users\\John Cena\\Desktop\\network.ann"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	}
	
	public void run() {
		running =  true;
		while(running) {
			String input = readConsole();
			String cmd = input.split(" ")[0];
			List<String> args = readArgs(input);
			runCommand(cmd, args);
		}
	}
	
	public void loadPlugins() {
		List<File> pluginJars = resourceLoader.loadPluginFiles(PLUGIN_DIR);
		pluginJars.forEach(jar -> {
			List<Class<?>> classes = classLoader.loadClassesFromJar(jar);
			findPluginClasses(classes).forEach(c -> {
				System.out.println(c.getName());
				addPluginClass((Class<? extends Plugin>) c);
			});
		});
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
	
	private List<Class<? extends Plugin>> findPluginClasses(List<Class<?>> classes){
		List<Class<? extends Plugin>> pluginClasses = new ArrayList<>();
		classes.stream()
			.filter(c -> Plugin.class.isAssignableFrom(c))
			.forEach(c -> {
				pluginClasses.add((Class<? extends Plugin>) c);
			});
		return pluginClasses;
	}
	
	private List<String> readArgs(String input) {
		List<String> args = new ArrayList<String>();
		args.addAll(Arrays.asList(input.split(" ")));
		args.remove(0);
		return args;
	}
	
	private void initPlugins() {
		plugins.forEach(plugin -> {
			plugin.load();
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
	
	private String readConsole() {
		System.out.print("JbonAI > ");
		return scanner.nextLine();
	}
	
	private void runCommand(String cmd, List<String> args) {
		try {
			registry.getCommand(cmd).execute(args);
		} catch (NoRegistryEntryException e) {
			e.printStackTrace();
		}
	}
}
