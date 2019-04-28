package ai.prima.prima.evolution.training;

import ai.prima.prima.Network;
import ai.prima.prima.data.Registry;
import ai.prima.prima.exceptions.NoRegistryEntryException;
import ai.prima.prima.functions.Function;
import ai.prima.prima.nodes.Connection;
import ai.prima.prima.nodes.InputNode;
import ai.prima.prima.nodes.Node;
import ai.prima.prima.nodes.OutputNode;
import ai.prima.prima.util.Log;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Mutator {

    private Function identityFunction;
    private final Random random = new Random();

    public Mutator(){
        try {
            identityFunction = Registry.getFunction("ai.prima.prima.functions.IdentityFunction");
        } catch (NoRegistryEntryException e) {
            e.printStackTrace();
        }
    }

    public void mutate(Network network, double rating) {
        double factor = Math.abs(rating);
        int rdm = random.nextInt(100);
        if (network.getNodes().size() > 0) {
            if (rdm < 10) {
                shrink(network);
            } else if (rdm < 20) {
                grow(network);
            } else if (rdm < 40) {
                addConnection(network);
            } else if (rdm < 50){
                removeConnection(network);
            } else {
                adjust(network, factor);
            }
        } else {
            Log.warning("Cannot mutate network since it contains no nodes.");
        }
    }

    private <T> T randomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private void grow(Network network) {
        List<Node> nodes = network.getNodes();
        Node node = new Node(identityFunction);
        randomElement(nodes).getConnections().add(new Connection(node));
        if (random.nextInt(2) == 0) {
            node.getConnections().add(new Connection(randomElement(nodes)));
        }
    }

    private void adjust(Network network, double factor) {
        List<Node> nodes = network.getNodes();
        List<Connection> connections = nodes.stream()
                .filter(node -> node.getConnections().size() > 0)
                .map(Node::getConnections).findAny().get();
        Connection connection = randomElement(connections);
        connection.adjustWeight((random.nextFloat() * 2 - 1) * (float) factor);
    }

    private void shrink(Network network) {
        List<Node> nodes = network.getNodes().stream()
                .filter(node -> !(node instanceof InputNode || node instanceof OutputNode))
                .collect(Collectors.toList());
        Node sacrifice = randomElement(nodes);
        network.getNodes().forEach(node -> {
            node.getConnections().removeAll(node.getConnections().stream()
                    .filter(connection -> connection.getTarget() == sacrifice)
                    .collect(Collectors.toList()));
        });
    }

    private void addConnection(Network network) {
        Node source = randomElement(network.getNodes());
        Node target = randomElement(network.getNodes().stream()
                .filter(node -> node != source)
                .collect(Collectors.toList()));
        source.getConnections().add(new Connection(target));
    }

    private void removeConnection(Network network) {
        Node source = randomElement(network.getNodes().stream()
                .filter(node -> !node.getConnections().isEmpty())
                .collect(Collectors.toList()));
        source.getConnections().remove(randomElement(source.getConnections()));
    }

}
