package ai.jbon.jbon;

public class NetworkThread extends Thread{

	private final ThreadConfig config;
	private final Network network;
	
	public NetworkThread(final Network network) {
		this.network = network;
		this.config = new ThreadConfig();
		setup();
	}
	
	public NetworkThread(final Network network, final ThreadConfig config) {
		this.network = network;
		this.config = config;
		setup();
	}
	
	@Override
	public void run() {
		network.run();
	}
	
	private void setup() {
		setPriority(config.priority);
	}
}