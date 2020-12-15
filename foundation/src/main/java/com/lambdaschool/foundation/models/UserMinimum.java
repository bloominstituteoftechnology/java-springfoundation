package com.lambdaschool.foundation.models;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * A model used to create a new user. The minimum information needed to create a user.
 * Note the role will default to USER.
 *
 * Should include ALL fields that the frontend wants to send the backend when a user self enrolls
 */
public class UserMinimum
{
    /**
     * The username (String)
     */
    @NotNull
    @Column(nullable = false)
    private String username;

    /**
     * The user's password (String)
     */
    @NotNull
    private String password;

    /**
     * The user's primary email address (String)
     */
    @Email
    @NotNull
    private String primaryemail;

    /**
     * Getter for the username
     *
     * @return the username (String) associated with this user
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Setter for the username
     *
     * @param username the new username (String) associated with this user
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Getter for the password of this user
     *
     * @return the password (String) for this user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Setter for the password of this user. This object is a temporary model used to create a new user.
     * The password must remain in clear text until saved into the database.
     *
     * @param password the new password (String in clear texts) for this user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Getter for email for this user
     *
     * @return the email address (String) for this user
     */
    public String getPrimaryemail()
    {
        return primaryemail;
    }

    /**
     * Setter for email for this user
     *
     * @param primaryemail the new email address (String) for this user.
     */
    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail;
    }
}
