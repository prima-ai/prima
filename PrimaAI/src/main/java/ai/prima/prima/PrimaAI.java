package ai.prima.prima;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ai.prima.prima.evolution.EvolutionPlugin;
import ai.prima.prima.evolution.training.Mutator;
import ai.prima.prima.evolution.training.StreamOutputRating;

import ai.prima.prima.commands.BuildCommand;
import ai.prima.prima.commands.DuplicateCommand;
import ai.prima.prima.commands.EchoCommand;
import ai.prima.prima.commands.ExitCommand;
import ai.prima.prima.commands.FunctionCommand;
import ai.prima.prima.commands.HelpCommand;
import ai.prima.prima.commands.NetworkCommand;
import ai.prima.prima.commands.SaveCommand;
import ai.prima.prima.commands.UseCommand;
import ai.prima.prima.commands.RunCommand;
import ai.prima.prima.commands.TestCommand;
import ai.prima.prima.commands.create.CreateCommand;
import ai.prima.prima.commands.delete.DeleteCommand;
import ai.prima.prima.data.Registry;
import ai.prima.prima.data.ResourceLoader;
import ai.prima.prima.functions.IdentityFunction;
import ai.prima.prima.inputNodes.ConsoleInputNode;
import ai.prima.prima.inputNodes.PositiveInputNode;
import ai.prima.prima.nodes.Node;
import ai.prima.prima.outputNodes.ConsoleOutputNode;
import ai.prima.prima.plugins.Plugin;
import ai.prima.prima.util.Log;
import ai.prima.prima.data.ClassLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class PrimaAI extends Application {

    private static final String BRAND = "\n" +
            "  _____      _                          _____ \n" +
            " |  __ \\    (_)                   /\\   |_   _|\n" +
            " | |__) | __ _ _ __ ___   __ _   /  \\    | |  \n" +
            " |  ___/ '__| | '_ ` _ \\ / _` | / /\\ \\   | |  \n" +
            " | |   | |  | | | | | | | (_| |/ ____ \\ _| |_ \n" +
            " |_|   |_|  |_|_| |_| |_|\\__,_/_/    \\_\\_____|\n" +
            "-----------------------------------------------";

    private static final File USERHOME = new File(System.getProperty("user.home"));
    public static final File HOME_DIR = new File(USERHOME + "\\primaai");
    public static final File PLUGIN_DIR = new File("..\\plugins");
    public static final File NETWORK_DIR = new File(HOME_DIR + "\\networks");
    public static final File SCRIPT_DIR = new File(HOME_DIR + "\\scripts");
    public static final File LOG_DIR = new File("..\\logs");

    public static final String NETWORK_EXTENSION = "network";

    private final ResourceLoader resourceLoader = new ResourceLoader();
    private final ClassLoader classLoader = new ClassLoader();
    private final List<Network> networks = new ArrayList<>();
    private final List<NetworkImage> images = new ArrayList<>();
    private final List<Plugin> plugins = new ArrayList<>();
    private final List<Script> scripts = new ArrayList<>();
    private final Prompt prompt = new Prompt();

    private Network selectedNetwork;
    private NetworkImage selectedImage;

    public PrimaAI() {
        Log.writeLine(BRAND);
        initDirectories();
        Registry.registerCommand(new EchoCommand());
        Registry.registerCommand(new TestCommand());
        Registry.registerCommand(new HelpCommand());
        Registry.registerCommand(new CreateCommand(this));
        Registry.registerCommand(new NetworkCommand(this));
        Registry.registerCommand(new UseCommand(this));
        Registry.registerCommand(new BuildCommand(this));
        Registry.registerCommand(new RunCommand(this));
        Registry.registerCommand(new SaveCommand(this));
        Registry.registerCommand(new DeleteCommand(this));
        Registry.registerCommand(new ExitCommand(this));
        Registry.registerCommand(new FunctionCommand());
        Registry.registerCommand(new DuplicateCommand(this));
        Registry.registerNode(ConsoleInputNode.class);
        Registry.registerNode(ConsoleOutputNode.class);
        Registry.registerNode(PositiveInputNode.class);
        Registry.registerNode(Node.class);
        Registry.registerFunction(new IdentityFunction());
        Registry.registerMutator(new Mutator());
        Registry.registerRating(StreamOutputRating.class);
        loadPlugins();
        initPlugins();
        initFunctions();
        initNodes();
        initCommands();
        loadNetworks();
        runPlugins();
        unloadPlugins();
    }

    @Override
    public void start(Stage primaryStage) {
        loadPlugins();
    /*    try {
            Font.loadFont(getClass().getResourceAsStream("fonts/Lato-Regular.ttf"), 12);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scenes/startscene/StartScene.fxml"));
            Scene startScene = new Scene(root);
            startScene.getStylesheets().add("themes/PrimaLight.css");
            primaryStage.setTitle("PrimaAI");
            primaryStage.setScene(startScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        prompt.run();
    }

    private void initDirectories() {
        HOME_DIR.mkdirs();
        PLUGIN_DIR.mkdirs();
        NETWORK_DIR.mkdirs();
        SCRIPT_DIR.mkdir();
        LOG_DIR.mkdirs();
    }

    public void loadPlugins() {
        plugins.add(new EvolutionPlugin());
        List<File> pluginJars = resourceLoader.loadPluginFiles(PLUGIN_DIR);
        pluginJars.forEach(jar -> {
            List<Class<?>> classes = classLoader.loadClassesFromJar(jar);
            findPluginClasses(classes).forEach(c -> {
                addPluginClass(c);
            });
        });
    }

    public void select(Network network) {
        this.selectedImage = null;
        this.selectedNetwork = network;
    }

    public void select(NetworkImage image) {
        this.selectedNetwork = null;
        this.selectedImage = image;
    }

    public void unselect() {
        this.selectedNetwork = null;
        this.selectedImage = null;
    }

    private void loadNetworks() {
        List<File> files = resourceLoader.getAllFilesFromDir(NETWORK_DIR);
        files.stream()
                .filter(file -> file.getName().endsWith("." + NETWORK_EXTENSION))
                .forEach(file -> {
                    loadNetwork(file);
                });
    }

    private void loadNetwork(File file) {
        try {
            Network network = resourceLoader.loadNetwork(file);
            networks.add(network);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPluginClass(Class<? extends Plugin> c) {
        try {
            plugins.add(c.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<Class<? extends Plugin>> findPluginClasses(List<Class<?>> classes) {
        List<Class<? extends Plugin>> pluginClasses = new ArrayList<>();
        classes.stream().filter(c -> Plugin.class.isAssignableFrom(c)).forEach(c -> {
            pluginClasses.add((Class<? extends Plugin>) c);
        });
        return pluginClasses;
    }

    private void initPlugins() {
        plugins.forEach(plugin -> {
            plugin.init(this);
        });
    }

    private void initCommands() {
        plugins.forEach(plugin -> {
            plugin.registerCommands();
        });
    }

    private void initFunctions() {
        plugins.forEach(plugin -> {
            plugin.registerFunctions();
        });
    }

    private void initNodes() {
        plugins.forEach(plugin -> {
            plugin.registerNodes();
        });
    }

    private void runPlugins() {
        plugins.forEach(plugin -> {
            plugin.run();
        });
    }

    private void unloadPlugins() {
        plugins.forEach(plugin -> {
            plugin.unload();
        });
    }

    public void exit() {
        Log.info("Stopping PrimaAI");
        Log.info("Saving Networks");
        networks.forEach(network -> {
            try {
                resourceLoader.storeNetwork(network);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Log.info("Cya");
        System.exit(0);
    }

    public List<NetworkImage> getNetworkImages() {
        return images;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public Network getSelectedNetwork() {
        return selectedNetwork;
    }

    public NetworkImage getSelectedImage() {
        return selectedImage;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
