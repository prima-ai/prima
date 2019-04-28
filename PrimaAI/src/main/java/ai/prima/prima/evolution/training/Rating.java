package ai.prima.prima.evolution.training;

import ai.prima.prima.NetworkImage;
import ai.prima.prima.evolution.Evolution;

public abstract class Rating {

    protected Evolution evolution;

    public Rating(Evolution evolution) {
        this.evolution = evolution;
    }

    public abstract void start();

    public abstract void stop();

    public abstract double rate(NetworkImage image);

    public void forceRatings(){
        evolution.getNetworkImages().forEach(image -> {
            evolution.rate(image, rate(image));
        });
    }
}
