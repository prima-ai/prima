package ai.prima.prima.inputNodes;

import java.util.Scanner;

import ai.prima.prima.functions.Function;
import ai.prima.prima.nodes.InputNode;

public class ConsoleInputNode extends InputNode{
	
	private Scanner scanner;
	private boolean running;

	public ConsoleInputNode(Function function) {
		super(function);
		running = true;
		scanner = new Scanner(System.in);
	}

	private synchronized void getInputValue() {
		System.out.print(Thread.currentThread() + " Enter value :");
		float value = scanner.nextFloat();
		input(value);
	}

	@Override
	public ConsoleInputNode clone(){
		return new ConsoleInputNode(function);
	}

	@Override
	public void start() {
		while(running) {
			getInputValue();
		}
	}
}
