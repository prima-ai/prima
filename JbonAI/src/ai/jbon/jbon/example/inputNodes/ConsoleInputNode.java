package ai.jbon.jbon.example.inputNodes;

import java.util.Scanner;

import ai.jbon.jbon.Injector;
import ai.jbon.jbon.nodes.InputNode;

public class ConsoleInputNode extends InputNode{

	private Scanner scanner;
	
	private boolean active = true;
	
	public ConsoleInputNode(Injector injector) {
		super(injector);
		this.scanner = new Scanner(System.in);
	}

	private void getInputValue() {
		System.out.print("Enter value :");
		float value = scanner.nextFloat();
		input(value);
	}

	@Override
	public void setup() {
		while(active) {
			getInputValue();
		}
	}
}
