package ai.prima.prima.commands;

public class Parameter {

    public enum Requirement {REQUIRED, OPTIONAL};

    private String name;
    private Requirement requirement;

    public Parameter(String name, Requirement requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}