package ai.prima.prima.injector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.Node;

public class Injector extends Thread {

    private final List<Node> queue;

    private boolean running;

    public Injector() {
        queue = Collections.synchronizedList(new ArrayList<Node>());
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (!queue.isEmpty()) {
                inject();
            }
        }
        queue.clear();
    }

    public synchronized void terminate() {
        running = false;
    }

    public synchronized void register(Node node) {
        queue.add(node);
    }

    public synchronized void register(Collection<Node> nodes) {
        queue.addAll(nodes);
    }

    private synchronized void inject() {
        List<Connection> connections = new ArrayList<Connection>();
        if(queue == null){
            System.out.println("wydd");
        }
        queue.forEach(node -> {
            List<Connection> outputs = node.pushOutput();
            if (outputs != null) {
                connections.addAll(outputs);
            }
        });
        List<Node> targets = gatherTargetNodes(connections);
        calcOutputs(targets);
        resetQueue(targets);
    }

    private void resetQueue(List<Node> targets) {
        queue.clear();
        queue.addAll(targets);
    }

    private void calcOutputs(List<Node> targets) {
        targets.forEach(target -> {
            target.calcOutput();
        });
    }

    private List<Node> gatherTargetNodes(final List<Connection> connections) {
        List<Node> targets = new ArrayList<Node>();
        connections.forEach(connection -> {
            targets.add(connection.getTarget());
        });
        return targets;
    }
}
