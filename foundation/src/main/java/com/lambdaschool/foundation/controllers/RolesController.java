package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Plants;
import com.lambdaschool.foundation.services.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * The entry point for clients to access role data
 * <p>
 * Note: we cannot update a role
 * we cannot update a role
 * working with the "non-owner" object in a many to many relationship is messy
 * we will be fixing that!
 */
@RestController
@RequestMapping("/roles")
public class RolesController
{
    /**
     * Using the Plants service to process Plants data
     */
    @Autowired
    PlantService plantService;

    /**
     * List of all roles
     * <br>Example: <a href="http://localhost:2019/roles/roles">http://localhost:2019/roles/roles</a>
     *
     * @return JSON List of all the roles and their associated users
     * @see PlantService#findAll() PlantService.findAll()
     */
    @GetMapping(value = "/roles",
        produces = "application/json")
    public ResponseEntity<?> listRoles()
    {
        List<Plants> allPlants = plantService.findAll();
        return new ResponseEntity<>(allPlants,
            HttpStatus.OK);
    }

    /**
     * The Plants referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleId The primary key (long) of the role you seek
     * @return JSON object of the role you seek
     * @see PlantService#findRoleById(long) PlantService.findRoleById(long)
     */
    @GetMapping(value = "/role/{roleId}",
        produces = "application/json")
    public ResponseEntity<?> getRoleById(
        @PathVariable
            Long roleId)
    {
        Plants r = plantService.findRoleById(roleId);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * The Plants with the given name
     * <br>Example: <a href="http://localhost:2019/roles/role/name/data">http://localhost:2019/roles/role/name/data</a>
     *
     * @param roleName The name of the role you seek
     * @return JSON object of the role you seek
     * @see PlantService#findByName(String) PlantService.findByName(String)
     */
    @GetMapping(value = "/role/name/{roleName}",
        produces = "application/json")
    public ResponseEntity<?> getRoleByName(
        @PathVariable
            String roleName)
    {
        Plants r = plantService.findByName(roleName);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * Given a complete Plants object, create a new Plants record
     * <br>Example: <a href="http://localhost:2019/roles/role">http://localhost:2019/roles/role</a>
     *
     * @param newPlants A complete new Plants object
     * @return A location header with the URI to the newly created role and a status of CREATED
     * @see PlantService#save(Plants) PlantService.save(Plants)
     */
    @PostMapping(value = "/role",
        consumes = "application/json")
    public ResponseEntity<?> addNewRole(
        @Valid
        @RequestBody
                Plants newPlants)
    {
        // ids are not recognized by the Post method
        newPlants.setRoleid(0);
        newPlants = plantService.save(newPlants);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{roleid}")
            .buildAndExpand(newPlants.getRoleid())
            .toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }

    /**
     * The process allows you to update a role name only!
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleid  The primary key (long) of the role you wish to update
     * @param newPlants The new name (String) for the role
     * @return Status of OK
     */
    @PutMapping(value = "/role/{roleid}",
        consumes = {"application/json"})
    public ResponseEntity<?> putUpdateRole(
        @PathVariable
            long roleid,
        @Valid
        @RequestBody
                Plants newPlants)
    {
        newPlants = plantService.update(roleid,
                newPlants);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
