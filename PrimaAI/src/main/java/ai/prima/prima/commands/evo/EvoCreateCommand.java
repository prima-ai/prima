package ai.prima.prima.commands.evo;

import ai.prima.prima.PrimaAI;
import ai.prima.prima.Network;
import ai.prima.prima.commands.Command;
import ai.prima.prima.evolution.Evolution;
import ai.prima.prima.evolution.EvolutionPlugin;
import ai.prima.prima.util.Log;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class EvoCreateCommand extends Command {

    private static final String NETWORK = "network";

    private final PrimaAI ai;

    public EvoCreateCommand(PrimaAI ai) {
        super("create", "creates a new evolution tree",
                Arrays.asList(),
                Arrays.asList(NETWORK),
                Arrays.asList());
        this.ai = ai;
    }

    @Override
    public void execute(Map<String, String> args) {
        if(args.containsKey(NETWORK)){
            Optional<Network> network = findNetwork(args.get(NETWORK));
            if(network.isPresent()){
                save(network.get());
            } else {
                Log.info("No such network");
            }
        } else {
            createFromName(args.get(NETWORK));
        }
    }

    private Optional<Network> findNetwork(String name) {
        return ai.getNetworks().stream()
                .filter(n -> n.getName().equals(name))
                .findAny();
    }

    private void save(Network network) {
        EvolutionPlugin.getEvolutions().add(new Evolution(network));
        Log.writeLine("Created evolution " + network.getName());
    }

    private void createFromName(String name){
        save(new Network(name));
    }
}
