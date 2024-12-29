package dto;

import exceptions.ValidationException;

public interface DtoBase
{
    void validate() throws ValidationException;
}
