package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Plants;

import java.util.List;

/**
 * The service that works with the Plants Model.
 * <p>
 * Note: no method update Plants name
 */
public interface PlantService
{
    /**
     * Returns a list of all Plants objects
     *
     * @return list of all Plants object
     */
    List<Plants> findAll();

    /**
     * Return the first Plants matching the given primary key
     *
     * @param id The primary key (long) of the Plants you seek
     * @return The Plants object you seek
     */
    Plants findRoleById(long id);

    /**
     * Given a complete Plants object, saved that Plants object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     * <p>
     * Note that Users are not added to Roles through this process
     *
     * @param plants the plants object to be saved
     * @return the saved plants object including any automatically generated fields
     */
    Plants save(Plants plants);

    /**
     * Find the first Plants object matching the given name
     *
     * @param name The name (String) of the role you seek
     * @return The Plants object matching the given name
     */
    Plants findByName(String name);
    
    /**
     * Updates the name of the given plants
     *
     * @param id   The primary key (long) of the plants you wish to update
     * @param plants The plants object containing the new name - only roles name can be updated through this process
     * @return The complete plants with the new name
     */
    Plants update(
        long id,
        Plants plants);
}