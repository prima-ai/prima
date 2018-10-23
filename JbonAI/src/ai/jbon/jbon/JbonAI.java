package ai.jbon.jbon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import ai.jbon.jbon.commands.Command;
import ai.jbon.jbon.commands.EchoCommand;
import ai.jbon.jbon.data.Registry;
import ai.jbon.jbon.data.ResourceLoader;
import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.functions.IdentityFunction;
import ai.jbon.jbon.functions.SigmoidFunction;
import ai.jbon.jbon.inputNodes.ConsoleInputNode;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;
import ai.jbon.jbon.outputNodes.ConsoleOutputNode;

public class JbonAI {

	private boolean running;
	
	private final Map<String, Command> commands = new HashMap<String, Command>();
	
	private NetworkThread threads;
	private final Scanner scanner;
	
	public JbonAI() {
		this.scanner = new Scanner(System.in);
		initCommands();
		initFunctions();
		initNodes();
	}
	
	public static void main(String args[]) {
		JbonAI ai = new JbonAI();
		
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
		
		ResourceLoader loader = new ResourceLoader();
		try {
			loader.storeNetwork(network, new File("C:\\Users\\John Cena\\Desktop\\network.ann"));
			Network n = loader.loadNetwork(new File("C:\\Users\\John Cena\\Desktop\\network.ann"));
			loader.storeNetwork(n, new File("C:\\Users\\John Cena\\Desktop\\network2.ann"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ai.run();
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
	
	private void initFunctions() {
		Registry.registerFunciton("sigmoid", new SigmoidFunction());
		Registry.registerFunciton("identity", new IdentityFunction());
	}
	
	private void initNodes() {
		Registry.registerNode(new ConsoleInputNode(null));
		Registry.registerNode(new Node(null));
		Registry.registerNode(new ConsoleOutputNode(null));
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
