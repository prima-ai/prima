package ai.prima.prima.evolution;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.evo.EvoCommand;
import ai.prima.prima.data.Registry;
import ai.prima.prima.plugins.Plugin;

import java.util.ArrayList;
import java.util.List;

public class EvolutionPlugin extends Plugin {

    private static final List<Evolution> evolutions = new ArrayList<>();
    private PrimaAI ai;

    public EvolutionPlugin() {
        super("Evolutions Plugin",
                "1.0",
                "Evolution Plugin",
                "PrimaAI",
                "https://www.prima.ai/");
    }


    @Override
    public void init(PrimaAI ai) {
        this.ai = ai;
    }

    @Override
    public void registerNodes() {

    }

    @Override
    public void registerFunctions() {

    }

    @Override
    public void registerCommands() {
        Registry.registerCommand(new EvoCommand(ai));
    }

    @Override
    public void unload() {

    }

    @Override
    public void run() {

    }

    public static List<Evolution> getEvolutions() {
        return evolutions;
    }
}
