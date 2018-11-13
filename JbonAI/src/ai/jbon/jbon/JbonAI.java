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
import ai.jbon.jbon.exceptions.RegistryFailedException;
import ai.jbon.jbon.functions.IdentityFunction;
import ai.jbon.jbon.functions.SigmoidFunction;
import ai.jbon.jbon.inputNodes.ConsoleInputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.outputNodes.ConsoleOutputNode;
import ai.jbon.jbon.data.ClassLoader;

public class JbonAI {

	private static final File PLUGIN_DIR = new File("../plugins");
	
	private final Map<String, Command> commands = new HashMap<String, Command>();
	private final Registry registry;
	private final List<NetworkThread> networkThreads;
	private final Scanner scanner;
	
	private boolean running;
	
	public JbonAI() {
		this.registry = new Registry();
		this.networkThreads = new ArrayList<>();
		this.scanner = new Scanner(System.in);
		initCommands();
		try {
			initFunctions(registry);
			initNodes(registry);
		} catch(RegistryFailedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		JbonAI ai = new JbonAI();
		
		if(!PLUGIN_DIR.exists()) {
			PLUGIN_DIR.mkdir();
		}
		ResourceLoader loader = new ResourceLoader();
		List<File> plugins = loader.getAllFilesFromDir(PLUGIN_DIR).stream()
				.filter(file -> file.getPath().endsWith(".jar"))
				.collect(Collectors.toList());
		ClassLoader classLoader = new ClassLoader();
		plugins.forEach(plugin -> {
			try {
				classLoader.loadClassesFromJar(plugin);
			} catch (LoadClassFromFileFailedException e) {
				e.printStackTrace();
			}
		});
		
		
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
	
	private List<String> readArgs(String input) {
		List<String> args = new ArrayList<String>();
		args.addAll(Arrays.asList(input.split(" ")));
		args.remove(0);
		return args;
	}
	
	private void initCommands() {
		commands.put("echo", new EchoCommand());
	}
	
	private void initFunctions(Registry registry) throws RegistryFailedException {
		registry.registerFunction(SigmoidFunction.class);
		registry.registerFunction(IdentityFunction.class);
	}
	
	private void initNodes(Registry registry) throws RegistryFailedException {
		registry.registerNode(ConsoleInputNode.class);
		registry.registerNode(Node.class);
		registry.registerNode(ConsoleOutputNode.class);
	}
	
	private String readConsole() {
		System.out.print("JbonAI > ");
		return scanner.nextLine();
	}
	
	private void runCommand(String cmd, List<String> args) {
		if(commands.containsKey(cmd)) {
			commands.get(cmd).execute(args);
		}else {
			Log.info("Unknown command");
		}
	}
}
