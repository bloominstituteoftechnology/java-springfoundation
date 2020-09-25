package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Useremail;

import java.util.List;


/**
 * The Service that works with the Useremail Model
 * <p>
 * Note: Emails are added through the add user process
 */
public interface UseremailService
{
    /**
     * Returns a list of all users and their emails
     *
     * @return List of users and their emails
     */
    List<Useremail> findAll();

    /**
     * Returns the user email combination associated with the given id
     *
     * @param id The primary key (long) of the user email combination you seek
     * @return The user email combination (Useremail) you seek
     */
    Useremail findUseremailById(long id);

    /**
     * Remove the user email combination referenced by the given id
     *
     * @param id The primary key (long) of the user email combination you seek
     */
    void delete(long id);

    /**
     * Replaces the email of the user email combination you seek
     *
     * @param useremailid  The primary key (long) of the user email combination you seek
     * @param emailaddress The new email address (String) for this user email combination
     * @return The Useremail object that you updated including the new email address
     */
    Useremail update(
        long useremailid,
        String emailaddress);

    /**
     * Add a new User Email combination
     *
     * @param userid       the userid of the new user email combination
     * @param emailaddress the email address of the new user email combination
     * @return the new user email combination
     */
    Useremail save(
        long userid,
        String emailaddress);
}
