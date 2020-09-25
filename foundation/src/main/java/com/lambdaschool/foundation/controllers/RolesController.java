package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Role;
import com.lambdaschool.foundation.services.RoleService;
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
     * Using the Role service to process Role data
     */
    @Autowired
    RoleService roleService;

    /**
     * List of all roles
     * <br>Example: <a href="http://localhost:2019/roles/roles">http://localhost:2019/roles/roles</a>
     *
     * @return JSON List of all the roles and their associated users
     * @see RoleService#findAll() RoleService.findAll()
     */
    @GetMapping(value = "/roles",
        produces = "application/json")
    public ResponseEntity<?> listRoles()
    {
        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles,
            HttpStatus.OK);
    }

    /**
     * The Role referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleId The primary key (long) of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findRoleById(long) RoleService.findRoleById(long)
     */
    @GetMapping(value = "/role/{roleId}",
        produces = "application/json")
    public ResponseEntity<?> getRoleById(
        @PathVariable
            Long roleId)
    {
        Role r = roleService.findRoleById(roleId);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * The Role with the given name
     * <br>Example: <a href="http://localhost:2019/roles/role/name/data">http://localhost:2019/roles/role/name/data</a>
     *
     * @param roleName The name of the role you seek
     * @return JSON object of the role you seek
     * @see RoleService#findByName(String) RoleService.findByName(String)
     */
    @GetMapping(value = "/role/name/{roleName}",
        produces = "application/json")
    public ResponseEntity<?> getRoleByName(
        @PathVariable
            String roleName)
    {
        Role r = roleService.findByName(roleName);
        return new ResponseEntity<>(r,
            HttpStatus.OK);
    }

    /**
     * Given a complete Role object, create a new Role record
     * <br>Example: <a href="http://localhost:2019/roles/role">http://localhost:2019/roles/role</a>
     *
     * @param newRole A complete new Role object
     * @return A location header with the URI to the newly created role and a status of CREATED
     * @see RoleService#save(Role) RoleService.save(Role)
     */
    @PostMapping(value = "/role",
        consumes = "application/json")
    public ResponseEntity<?> addNewRole(
        @Valid
        @RequestBody
            Role newRole)
    {
        // ids are not recognized by the Post method
        newRole.setRoleid(0);
        newRole = roleService.save(newRole);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{roleid}")
            .buildAndExpand(newRole.getRoleid())
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
     * @param newRole The new name (String) for the role
     * @return Status of OK
     */
    @PutMapping(value = "/role/{roleid}",
        consumes = {"application/json"})
    public ResponseEntity<?> putUpdateRole(
        @PathVariable
            long roleid,
        @Valid
        @RequestBody
            Role newRole)
    {
        newRole = roleService.update(roleid,
            newRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
