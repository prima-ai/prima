package ai.prima.prima.commands;

import java.util.Comparator;

public class ParameterComparator implements Comparator<Parameter> {
    @Override
    public int compare(Parameter o1, Parameter o2) {
        if (o1.getRequirement() == Parameter.Requirement.REQUIRED) {
            if (o2.getRequirement() == Parameter.Requirement.REQUIRED) {
                return 0;
            }
            return -1;
        }
        return 1;
    }
}
