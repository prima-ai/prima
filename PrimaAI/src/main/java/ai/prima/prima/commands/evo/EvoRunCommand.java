package ai.prima.prima.commands.evo;

import ai.prima.prima.commands.Command;
import ai.prima.prima.commands.Parameter;
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

    private static final Parameter NAME_PARAMETER = new Parameter("name", Parameter.Requirement.REQUIRED);
    private static final Parameter RATING_PARAMETER = new Parameter("rating", Parameter.Requirement.OPTIONAL);
    private static final Parameter MUTATOR_PARAMETER = new Parameter("mutator", Parameter.Requirement.OPTIONAL);

    public EvoRunCommand() {
        super("run", "Runs an Evolution", Arrays.asList(NAME_PARAMETER, RATING_PARAMETER, MUTATOR_PARAMETER));
    }

    @Override
    public void execute(Map<Parameter, String> values) {
        String name = values.get(NAME_PARAMETER);
        Optional<Evolution> optional = findEvolution(values.get(NAME_PARAMETER));
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
