package com.lambdaschool.foundation.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A class used to display error messages in our own chosen format
 */
public class ErrorDetail
{
    /**
     * The title (String) of the error message
     */
    private String title;

    /**
     * HTTP Status of this error
     */
    private int status;

    /**
     * Detailed message for an end user, client, explaining the error
     */
    private String detail;

    /**
     * Date and time stamp for this error
     */
    private Date timestamp;

    /**
     * A message for developers about this error message, things like class and code causing the error.
     * Specifically written so as not to give away security information.
     */
    private String developerMessage;

    /**
     * If data validation errors caused this error, the list of them will appear here.
     */
    private List<ValidationError> errors = new ArrayList<>();

    /**
     * Default constructor for this class
     */
    public ErrorDetail()
    {
    }

    /**
     * Getter for title
     *
     * @return Title (String) of this error
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Setter for title
     *
     * @param title the new title (String) for this error
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Getter for status
     *
     * @return the Http Status code (int) for this error
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Setter for status
     *
     * @param status The new Http Status code (int) for this error
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Getter for error details
     *
     * @return A detailed message (String) about this error suitable for regular users, clients
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * Setter for error details
     *
     * @param detail The new detailed message (String) about this error suitable for regular users, clients
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * Getter for the date and time when this error happened
     *
     * @return The data and time (date) when this error happened
     */
    public Date getTimestamp()
    {
        return timestamp;
    }

    /**
     * Setter for the date and time when this error happened
     *
     * @param timestamp the changed data and time (date) when this error happened
     */
    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Getter for the developer's message
     *
     * @return A message for developers about this error message (String), things like class and code causing the error.
     */
    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    /**
     * Setter for the developer's message
     *
     * @param developerMessage The new message for developers about this error message (String), things like class and code causing the error.
     */
    public void setDeveloperMessage(String developerMessage)
    {
        this.developerMessage = developerMessage;
    }

    /**
     * Getter for the list of validation errors
     *
     * @return The list of validation errors, if any, for this error
     */
    public List<ValidationError> getErrors()
    {
        return errors;
    }

    /**
     * Setter for the list of validation errors
     *
     * @param errors The new list of validation errors, if any, for this error
     */
    public void setErrors(List<ValidationError> errors)
    {
        this.errors = errors;
    }
}
