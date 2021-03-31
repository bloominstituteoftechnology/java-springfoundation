package com.lambdaschool.foundation.models;

import javax.validation.constraints.Email;

/**
 * A model used to create a new user. The minimum information needed to create a user.
 * Note the role will default to USER.
 */
public class UserMinimum {
    /**
     * The username (String)
     */
    private String firstname;

    /**
     * The user's password (String)
     */
    private String password;

    /**
     * The user's primary email address (String)
     */
    @Email
    private String email;


    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }




    public String getLastname() {
        return lastname;
    }





    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for the username
     *
     * @return the username (String) associated with this user
     */


    /**
     * Setter for the username
     *
     * @param username the new username (String) associated with this user
     */

    /**
     * Getter for the password of this user
     *
     * @return the password (String) for this user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of this user. This object is a temporary model used to create a new user.
     * The password must remain in clear text until saved into the database.
     *
     * @param password the new password (String in clear texts) for this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for email for this user
     *
     * @return the email address (String) for this user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email for this user
     *
     * @param primaryemail the new email address (String) for this user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
