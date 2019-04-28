package ai.prima.prima.evolution.training;

import ai.prima.prima.NetworkImage;
import ai.prima.prima.evolution.Evolution;

import java.io.OutputStream;

public class OutputStreamStub extends OutputStream {

    private Evolution evolution;
    private NetworkImage image;

    private boolean rated;

    StringBuilder buffer = new StringBuilder();

    public OutputStreamStub(Evolution evolution, NetworkImage image){
        this.evolution = evolution;
        this.image = image;
    }

    @Override
    public void write(int b) {
        if(!rated) {
            buffer.append(Character.toChars(b));
        }
    }

    @Override
    public void flush(){
        if(!rated) {
            Double rating = -Math.abs(2D - Double.parseDouble(buffer.toString()));
            System.out.println(image.getName() + ": " + buffer.toString());
            evolution.rate(image, rating);
            buffer = new StringBuilder();
            rated = true;
        }
    }
}
