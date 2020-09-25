package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Role;

import java.util.List;

/**
 * The service that works with the Role Model.
 * <p>
 * Note: no method update Role name
 */
public interface RoleService
{
    /**
     * Returns a list of all Role objects
     *
     * @return list of all Role object
     */
    List<Role> findAll();

    /**
     * Return the first Role matching the given primary key
     *
     * @param id The primary key (long) of the Role you seek
     * @return The Role object you seek
     */
    Role findRoleById(long id);

    /**
     * Given a complete Role object, saved that Role object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     * <p>
     * Note that Users are not added to Roles through this process
     *
     * @param role the role object to be saved
     * @return the saved role object including any automatically generated fields
     */
    Role save(Role role);

    /**
     * Find the first Role object matching the given name
     *
     * @param name The name (String) of the role you seek
     * @return The Role object matching the given name
     */
    Role findByName(String name);

    /**
     * Deletes all record and their associated records from the database
     */
    public void deleteAll();

    /**
     * Updates the name of the given role
     *
     * @param id   The primary key (long) of the role you wish to update
     * @param role The role object containing the new name - only roles name can be updated through this process
     * @return The complete role with the new name
     */
    Role update(
        long id,
        Role role);
}