package exceptions;

import java.util.List;

public class ValidationException extends Exception
{

    private final List<ValidationError> errors;

    public ValidationException(List<ValidationError> errors)
    {
        this.errors = errors;
    }

    public List<ValidationError> getErrors()
    {
        return errors;
    }

    @Override
    public String getMessage()
    {
        StringBuilder message = new StringBuilder("Validation failed: ");
        errors.forEach((error) ->
        {
            message.append("\n").append(error.getField()).append(": ").append(error.getMessage());
        });
        return message.toString();
    }
}
