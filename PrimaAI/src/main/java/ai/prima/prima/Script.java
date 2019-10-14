package ai.prima.prima;

import java.util.List;

public class Script {

    private PrimaAI ai;
    private String name;
    private List<String> lines;

    public Script(PrimaAI ai, String name, List<String> lines) {
        this.ai = ai;
        this.name = name;
        this.lines = lines;
    }

    public void run(){
        lines.forEach( line -> {
            ai.getPrompt().runCmd(line);
        });
    }
}
