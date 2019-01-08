package ai.jbon.jbon;

import ai.jbon.jbon.util.Log;

public class NetworkImage extends Thread{

	private final Network network;
	
	public NetworkImage(final String name, final Network network) {
		this.setName(name);
		this.network = network;
		setup();
	}
	
	@Override
	public void run() {
		network.run();
	}
	
	private void setup() {

	}
	
	public Network getNetwork() {
		return this.network;
	}	
}