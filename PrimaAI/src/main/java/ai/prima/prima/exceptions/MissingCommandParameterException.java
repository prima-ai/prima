package ai.prima.prima.exceptions;

import ai.prima.prima.commands.Parameter;

import java.util.List;

public class MissingCommandParameterException extends Throwable {
    public MissingCommandParameterException(List<Parameter> parameters) {

    }
}
