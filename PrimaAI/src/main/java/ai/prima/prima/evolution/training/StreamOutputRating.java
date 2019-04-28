package ai.prima.prima.evolution.training;

import ai.prima.prima.NetworkImage;
import ai.prima.prima.evolution.Evolution;
import ai.prima.prima.outputNodes.ConsoleOutputNode;

public class StreamOutputRating extends Rating {

    public StreamOutputRating(Evolution evolution) {
        super(evolution);
    }

    private void stubOutputStreams() {
        evolution.getNetworkImages()
                .forEach(image -> {
                    image.getNetwork().getNodes().forEach(node -> {
                        if (ConsoleOutputNode.class.isAssignableFrom(node.getClass())) {
                            ((ConsoleOutputNode) node).createWriter(new OutputStreamStub(evolution, image));
                        }
                    });
                });
    }

    @Override
    public void start() {
        stubOutputStreams();
    }

    @Override
    public void stop() {

    }

    @Override
    public double rate(NetworkImage image) {
        return 0;
    }
}
