package com.lambdaschool.foundation.models;

/**
 * A model to report a validation error
 */
public class ValidationError
{
    /**
     * The code (String) for the validation error
     */
    private String Code;

    /**
     * The message (String) fro the validation error
     */
    private String message;

    /**
     * Getter for the code
     *
     * @return the code (String) for this validation error
     */
    public String getCode()
    {
        return Code;
    }

    /**
     * Setter for the code
     *
     * @param code the new code (String) for this validation error
     */
    public void setCode(String code)
    {
        Code = code;
    }

    /**
     * Getter for the message
     *
     * @return The message (String) associated with this validation error
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Setter for the message
     *
     * @param message The new message (String) associated with this validation error
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Displays the current validation error
     *
     * @return The current validation error as a String
     */
    @Override
    public String toString()
    {
        return "ValidationError{" + "Code='" + Code + '\'' + ", message='" + message + '\'' + '}';
    }
}
