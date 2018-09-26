package ai.jbon.jbon.example;

import ai.jbon.jbon.Connection;
import ai.jbon.jbon.Injector;
import ai.jbon.jbon.example.inputNodes.ConsoleInputNode;
import ai.jbon.jbon.example.outputNodes.ConsoleOutputNode;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;
import ai.jbon.jbon.nodes.OutputNode;

public class Example {

	public static void main(String[] args) {
		System.out.println("Startup");
		Injector injector = new Injector();
		InputNode input1 = new ConsoleInputNode(injector);
		OutputNode output1 = new ConsoleOutputNode();
		Node node1 = new Node();
		System.out.println("Initialited Nodes");
		node1.addConnection(new Connection(output1, 1.0F));
		input1.addConnection(new Connection(node1, 1.0F));
		System.out.println("Initialized Connections");
		injector.run();
		input1.setup();
	}
}
