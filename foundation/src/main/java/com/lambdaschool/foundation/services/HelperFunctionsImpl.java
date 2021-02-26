package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl
    implements HelperFunctions
{
    /**
     * A public field used to store data from another API. This will have to be populated each time the application is run.
     * Population is done manually for each country code using an endpoint.
     */
    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        // Find any data violations that might be associated with the error and report them
        // data validations get wrapped in other exceptions as we work through the Spring
        // exception chain. Hence we have to search the entire Spring Exception Stack
        // to see if we have any violation constraints.
        while ((cause != null) && !(cause instanceof org.hibernate.exception.ConstraintViolationException || cause instanceof MethodArgumentNotValidException))
        {
            System.out.println(cause.getClass()
                .toString());
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        // we know that cause either null or an instance of ConstraintViolationException
        if (cause != null)
        {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException)
            {
                org.hibernate.exception.ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError newVe = new ValidationError();
                newVe.setCode(ex.getMessage());
                newVe.setMessage(ex.getConstraintName());
                listVE.add(newVe);
            } else
            {
                if (cause instanceof MethodArgumentNotValidException)
                {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                    List<FieldError> fieldErrors = ex.getBindingResult()
                        .getFieldErrors();
                    for (FieldError err : fieldErrors)
                    {
                        ValidationError newVe = new ValidationError();
                        newVe.setCode(err.getField());
                        newVe.setMessage(err.getDefaultMessage());
                        listVE.add(newVe);
                    }
                } else
                {
                    System.out.println("Error in producing constraint violations exceptions. " +
                        "If we see this in the console a major logic error has occurred in the " +
                        "helperfunction.getConstraintViolation method that we should investigate. " +
                        "Note the application will keep running as this only affects exception reporting!");
                }
            }
        }
        return listVE;
    }

    @Override
    public boolean isAuthorizedToMakeChange(String username)
    {
        // Check to see if the user whose information being requested is the current user
        // Check to see if the requesting user is an admin
        // if either is true, return true
        // otherwise stop the process and throw an exception
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        if (username.equalsIgnoreCase(authentication.getName()
            .toLowerCase()) || authentication.getAuthorities()
            .contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
        {
            // this user can make this change
            return true;
        } else
        {
            // stop user is not authorized to make this change so stop the whole process and throw an exception
            throw new OAuth2AccessDeniedException();
        }
    }
}
