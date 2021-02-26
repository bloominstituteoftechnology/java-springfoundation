package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Useremail;
import com.lambdaschool.foundation.services.UseremailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for client to access user, email combinations
 */
@RestController
@RequestMapping("/useremails")
public class UseremailController
{
    /**
     * Using the Useremail service to process user, email combinations data
     */
    @Autowired
    UseremailService useremailService;

    /**
     * List of all users emails
     * <br>Example: <a href="http://localhost:2019/useremails/useremails">http://localhost:2019/useremails/useremails</a>
     *
     * @return JSON list of all users emails
     */
    @GetMapping(value = "/useremails",
        produces = "application/json")
    public ResponseEntity<?> listAllUseremails()
    {
        List<Useremail> allUserEmails = useremailService.findAll();
        return new ResponseEntity<>(allUserEmails,
            HttpStatus.OK);
    }

    /**
     * Return the user email combination referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/useremails/useremail/8">http://localhost:2019/useremails/useremail/8</a>
     *
     * @param useremailId the primary key of the user email combination you seek
     * @return JSON object of the user email combination you seek with a status of OK
     */
    @GetMapping(value = "/useremail/{useremailId}",
        produces = "application/json")
    public ResponseEntity<?> getUserEmailById(
        @PathVariable
            Long useremailId)
    {
        Useremail ue = useremailService.findUseremailById(useremailId);
        return new ResponseEntity<>(ue,
            HttpStatus.OK);
    }

    /**
     * Removes the given user email combination
     * <br>Example: <a href="http://localhost:2019/useremails/useremail/8">http://localhost:2019/useremails/useremail/8</a>
     *
     * @param useremailid the primary key of the user email combination you wish to remove
     * @return Status of OK
     */
    @DeleteMapping(value = "/useremail/{useremailid}")
    public ResponseEntity<?> deleteUserEmailById(
        @PathVariable
            long useremailid)
    {
        useremailService.delete(useremailid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Change the email associated with the given user email combination
     * <br>Example: <a href="http://localhost:2019/useremails/useremail/9/email/favbun@hops.local">http://localhost:2019/useremails/useremail/9/email/favbun@hops.local</a>
     *
     * @param useremailid  The primary key of the user email combination you wish to change
     * @param emailaddress The new email (String)
     * @return Status of OK
     */
    @PutMapping("/useremail/{useremailid}/email/{emailaddress}")
    public ResponseEntity<?> updateUserEmail(
        @PathVariable
            long useremailid,
        @PathVariable
            String emailaddress)
    {
        useremailService.update(useremailid,
            emailaddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds a new user email combination
     *
     * @param userid       the user id of the new user email combination
     * @param emailaddress the email address of the new user eamil combination
     * @return A location header with the URI to the newly created user email combination and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see UseremailService#save(long, String) UseremailService.save(long, String)
     */
    @PostMapping(value = "/user/{userid}/email/{emailaddress}")
    public ResponseEntity<?> addNewUserEmail(
        @PathVariable
            long userid,
        @PathVariable
            String emailaddress) throws
                                 URISyntaxException
    {
        Useremail newUserEmail = useremailService.save(userid,
            emailaddress);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
            .path("/useremails/useremail/{useremailid}")
            .buildAndExpand(newUserEmail.getUseremailid())
            .toUri();
        responseHeaders.setLocation(newUserEmailURI);

        return new ResponseEntity<>(null,
            responseHeaders,
            HttpStatus.CREATED);
    }
}
