package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.ErrorDetail;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access user data
 */
@RestController
@RequestMapping("/users")
public class UserController
{
    /**
     * Using the User service to process user data
     */
    @Autowired
    private UserService userService;

    /**
     * Returns a list of all users
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all users with a status of OK
     * @see UserService#findAll() UserService.findAll()
     */
    @ApiOperation(value = "returns all Users",
            response = User.class,
            responseContainer = "List")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users",
            produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                                    HttpStatus.OK);
    }

    /**
     * Returns a single user based off a user id number
     * <br>Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param userId The primary key of the user you seek
     * @return JSON object of the user you seek
     * @see UserService#findUserById(long) UserService.findUserById(long)
     */
    @ApiOperation(value = "Retrieve a user based of off user id",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @ApiParam(value = "User id",
                    required = true,
                    example = "4")
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    /**
     * Return a user object based on a given username
     * <br>Example: <a href="http://localhost:2019/users/user/name/cinnamon">http://localhost:2019/users/user/name/cinnamon</a>
     *
     * @param userName the name of user (String) you seek
     * @return JSON object of the user you seek
     * @see UserService#findByName(String) UserService.findByName(String)
     */
    @ApiOperation(value = "returns the user with the given username",
            response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/name/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
            @ApiParam(value = "user name",
                    required = true,
                    example = "johnmitchell")
            @PathVariable
                    String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    /**
     * Returns a list of users whose username contains the given substring
     * <br>Example: <a href="http://localhost:2019/users/user/name/like/da">http://localhost:2019/users/user/name/like/da</a>
     *
     * @param userName Substring of the username for which you seek
     * @return A JSON list of users you seek
     * @see UserService#findByNameContaining(String) UserService.findByNameContaining(String)
     */
    @ApiOperation(value = "returns all Users whose username contains the given substring",
            response = User.class,
            responseContainer = "List")
    @ApiParam(value = "User Name Substring",
            required = true,
            example = "john")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/name/like/{userName}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    /**
     * Given a complete User Object, create a new User record and accompanying useremail records
     * and user role records.
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param newuser A complete new user to add including emails and roles.
     *                roles must already exist.
     * @return A location header with the URI to the newly created user and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see UserService#save(User) UserService.save(User)
     */
    @ApiOperation(value = "adds a user given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PostMapping(value = "/user",
            consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(
            @Valid
            @RequestBody
                    User newuser)
            throws
            URISyntaxException
    {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    /**
     * Given a complete User Object
     * Given the user id, primary key, is in the User table,
     * replace the User record , user role combinations and Useremail records.
     * <br> Example: <a href="http://localhost:2019/users/user/15">http://localhost:2019/users/user/15</a>
     *
     * @param updateUser A complete User including all emails and roles to be used to
     *                   replace the User. Roles must already exist.
     * @param userid     The primary key of the user you wish to replace.
     * @return status of OK
     * @see UserService#save(User) UserService.save(User)
     */
    @ApiOperation(value = "updates a user given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PutMapping(value = "/user/{userid}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateFullUser(
            @Valid
            @ApiParam(value = "a full user object",
                    required = true)
            @RequestBody
                    User updateUser,
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long userid)
    {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates the user record associated with the given id with the provided data. Only the provided fields are affected.
     * If an email list or user role combination list is given, it replaces the list.
     * <br> Example: <a href="http://localhost:2019/users/user/7">http://localhost:2019/users/user/7</a>
     *
     * @param updateUser An object containing values for just the fields that are being updated. All other fields are left NULL.
     * @param id         The primary key of the user you wish to update.
     * @return A status of OK
     * @see UserService#update(User, long) UserService.update(User, long)
     */
    @ApiOperation(value = "updates a user with the information given in the request body",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @PatchMapping(value = "/user/{id}",
            consumes = {"application/json"})
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "a user object with just the information needed to be updated",
                    required = true)
            @RequestBody
                    User updateUser,
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long id)
    {
        userService.update(updateUser,
                           id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a given user along with associated emails and roles
     * <br>Example: <a href="http://localhost:2019/users/user/14">http://localhost:2019/users/user/14</a>
     *
     * @param id the primary key of the user you wish to delete
     * @return Status of OK
     */
    @ApiOperation(value = "Deletes the given user",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes the given user, user role combination
     * <br>Example: <a href="http://localhost:2019/users/user/7/role/2">http://localhost:2019/users/user/7/role/2</a>
     *
     * @param userid the user id of the user of the user role combination
     * @param roleid the role id of the user of the user user role combination
     * @return Status OK
     */
    @ApiOperation(value = "Deletes the given user",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User Found",
            response = User.class), @ApiResponse(code = 404,
            message = "User Not Found",
            response = ErrorDetail.class)})
    @DeleteMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long userid,
            @ApiParam(value = "roleid",
                    required = true,
                    example = "4")
            @PathVariable
                    long roleid)
    {
        userService.deleteUserRole(userid,
                                   roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds the given user, user role combination
     * <br>Example: <a href="http://localhost:2019/users/user/7/role/2">http://localhost:2019/users/user/7/role/2</a>
     *
     * @param userid the user id of the user user role combination
     * @param roleid the role id of the user user role combination
     * @return Status OK
     */
    @ApiOperation(value = "Adds a given user role to the given user",
            response = Void.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "User And Role Found",
            response = Void.class), @ApiResponse(code = 404,
            message = "User and / or Role Not Found",
            response = ErrorDetail.class)})
    @PostMapping(value = "/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(
            @ApiParam(value = "userid",
                    required = true,
                    example = "4")
            @PathVariable
                    long userid,
            @ApiParam(value = "roleid",
                    required = true,
                    example = "4")
            @PathVariable
                    long roleid)
    {
        userService.addUserRole(userid,
                                roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Returns the User record for the currently authenticated user based off of the supplied access token
     * <br>Example: <a href="http://localhost:2019/users/getuserinfo">http://localhost:2019/users/getuserinfo</a>
     *
     * @param authentication The authenticated user object provided by Spring Security
     * @return JSON of the current user. Status of OK
     * @see UserService#findByName(String) UserService.findByName(authenticated user)
     */
    @ApiOperation(value = "returns the currently authenticated user",
            response = User.class)
    @GetMapping(value = "/getuserinfo",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }
}