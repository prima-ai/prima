package ai.prima.prima.outputNodes;

import ai.prima.prima.functions.Function;
import ai.prima.prima.nodes.OutputNode;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ConsoleOutputNode extends OutputNode {

	private OutputStreamWriter writer;
	private OutputStream stream;

	public ConsoleOutputNode(Function function, OutputStream out) {
		super(function);
		createWriter(out);
	}

	public ConsoleOutputNode(Function function){
		super(function);
		createWriter(System.out);
	}

	@Override
	public void execute(float value) {
		try {
			writer.write(Float.toString(value) + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OutputNode clone() {
		return new ConsoleOutputNode(this.function, stream);
	}

	public synchronized void createWriter(OutputStream out){
		stream = out;
		writer = new OutputStreamWriter(out);
	}
}
