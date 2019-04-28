package ai.prima.prima.evolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ai.prima.prima.Network;
import ai.prima.prima.NetworkImage;
import ai.prima.prima.evolution.training.Mutator;
import ai.prima.prima.evolution.training.Rating;
import ai.prima.prima.util.Log;

public class Evolution {

    private String name;
    private int generation = 1;
    private List<NetworkImage> images = new ArrayList<>();
    private Network baseNetwork;
    private Mutator mutator = new Mutator();
    private Rating rating;
    private Map<NetworkImage, Double> ratings = new HashMap<>();

    private int mutations = 10;

    public Evolution(Network network) {
        this.name = network.getName();
        this.baseNetwork = network;
    }

    public void run() {
        evolve(baseNetwork, 1);
    }

    private void start() {
        if (rating != null) {
            rating.start();
        } else {
            Log.warning("Running evolution '" + name + "' without rating!");
        }
        images.forEach(image -> {
            Log.info("Started " + image.getId());
        });
        images.forEach(image -> {
            image.start();
        });
    }

    public void stop() {
        images.forEach(image -> {
            image.terminate();
            Log.info("Stopped " + image.getName());
        });
    }

    private void buildNetworkImages(List<Network> networks) {
        images = networks.stream()
                .map(child -> NetworkImage.build(child.getName(), child))
                .collect(Collectors.toList());
    }

    private List<Network> buildNetworks(Network network, double rating){
        List<Network> networks = new ArrayList<>();
        IntStream.range(0, mutations).forEach(index -> {
            Network child = network.clone();
            child.setName(child.getName());
            mutator.mutate(child, rating);
            networks.add(child);
        });
        return networks;
    }

    public void evolve(Network network, double rating) {
        stop();
        List<Network> networks = buildNetworks(network, rating);
        buildNetworkImages(networks);
        ratings.clear();
        start();
    }

    public void rate(NetworkImage image, Double rating) {
        if (images.contains(image)) {
            ratings.put(image, rating);
            if (ratings.keySet().containsAll(images)) {
                Log.info("Evolving ");
                createNextGeneration();
            }
        } else {
            Log.warning("Image " + image.getName() + " was rated but is not part of the current generation");
        }
    }

    private void createNextGeneration() {
        double bestRating = Collections.max(ratings.values());
        evolve(ratings.entrySet().stream()
                .filter(e -> e.getValue().equals(bestRating))
                .findAny().get().getKey().getNetwork(), bestRating);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NetworkImage> getNetworkImages() {
        return images;
    }

    public void setNetworkImages(List<NetworkImage> images) {
        this.images = images;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    public int getMutations() {
        return mutations;
    }

    public void setMutations(int mutations) {
        this.mutations = mutations;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
