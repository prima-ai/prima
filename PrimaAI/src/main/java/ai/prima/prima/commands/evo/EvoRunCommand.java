package ai.prima.prima.commands.evo;

import ai.prima.prima.commands.Command;
import ai.prima.prima.evolution.EvolutionPlugin;
import ai.prima.prima.evolution.Evolution;
import ai.prima.prima.evolution.training.Mutator;
import ai.prima.prima.evolution.training.Rating;
import ai.prima.prima.evolution.training.StreamOutputRating;
import ai.prima.prima.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class EvoRunCommand extends Command {

    private static final String NAME = "name";
    private static final String RATING = "rating";
    private static final String MUTATOR = "mutator";

    public EvoRunCommand() {
        super("run", "Runs an Evolution",
                Arrays.asList(NAME),
                Arrays.asList(RATING, MUTATOR),
                Arrays.asList());
    }

    // TODO clean this up during registry refactoring
    @Override
    public void execute(Map<String, String> args) {
        String name = args.get(NAME);
        Optional<Evolution> optional = findEvolution(args.get(NAME));
        if (optional.isPresent()) {
            Evolution evolution = optional.get();
            evolution.setRating(new StreamOutputRating(evolution));
            evolution.setMutator(new Mutator());
            evolution.run();
        } else {
            Log.error("No such evolution '" + name + "'");
        }
    }

    private Optional<Evolution> findEvolution(String name) {
        return EvolutionPlugin.getEvolutions().stream()
                .filter(evolution -> evolution.getName().equals(name))
                .findAny();
    }

    private Rating instantiateRating(Class<? extends Rating> c, Evolution evolution) {
        Constructor<? extends Rating> constructor = null;
        try {
            constructor = c.getDeclaredConstructor(Evolution.class);
            return constructor.newInstance(evolution);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
