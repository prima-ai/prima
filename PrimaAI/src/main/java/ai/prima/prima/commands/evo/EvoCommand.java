package ai.prima.prima.commands.evo;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.commands.Command;
import ai.prima.prima.evolution.EvolutionPlugin;
import ai.prima.prima.util.Log;

import java.util.Arrays;
import java.util.Map;

public class EvoCommand extends Command {

    public EvoCommand(PrimaAI ai) {
        super("evo", "Lists evolutions",
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(new EvoCreateCommand(ai), new EvoRunCommand()));
    }

    @Override
    public void execute(Map<String, String> args) {
        EvolutionPlugin.getEvolutions().forEach(evolution -> {
            Log.writeLine(evolution.getName());
        });
    }
}
