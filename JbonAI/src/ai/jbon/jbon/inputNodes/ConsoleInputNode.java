package ai.jbon.jbon.inputNodes;

import java.util.Scanner;

import ai.jbon.jbon.functions.Function;
import ai.jbon.jbon.injector.Injector;
import ai.jbon.jbon.nodes.InputNode;
import ai.jbon.jbon.nodes.Node;

public class ConsoleInputNode extends InputNode{

	private static final String TAG = "consolein";
	
	private Scanner scanner;
	private boolean running = true;
	
	public ConsoleInputNode(Function function) {
		super(TAG, function);
		this.scanner = new Scanner(System.in);
	}

	private void getInputValue() {
		System.out.print("Enter value :");
		float value = scanner.nextFloat();
		input(value);
	}

	@Override
	public void start() {
		while(running) {
			getInputValue();
		}
	}
}
