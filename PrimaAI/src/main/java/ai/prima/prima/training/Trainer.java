package ai.prima.prima.training;

import ai.prima.prima.NetworkImage;

public abstract class Trainer {

    protected NetworkImage image;

    public Trainer(NetworkImage image){
        this.image = image;
    }

    public abstract void start();

    public abstract void stop();
}
