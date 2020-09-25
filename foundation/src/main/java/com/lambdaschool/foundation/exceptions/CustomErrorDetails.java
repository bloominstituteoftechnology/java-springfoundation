package com.lambdaschool.foundation.exceptions;


import com.lambdaschool.foundation.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to override the error details provided by Spring Boot. We want to use our own format.
 */
@Component
public class CustomErrorDetails
    extends DefaultErrorAttributes
{
    /**
     * Connects this class with the Helper Functions
     */
    @Autowired
    private HelperFunctions helperFunctions;

    /**
     * Custom method to override the error details provided by Spring Boot. We want to use our own format.
     *
     * @param webRequest        The information related to the request that was made and the exceptions that occurred.
     * @param includeStackTrace Should we include the Stack Trace in our output. This field is not used in our implementation.
     * @return a Map of String, Object with our information to report in place of the standard Spring Boot information.
     */
    @Override
    public Map<String, Object> getErrorAttributes(
        WebRequest webRequest,
        boolean includeStackTrace)
    {

        //Get all the normal error information
        Map<String, Object> errorAttributes =
            super.getErrorAttributes(webRequest,
                includeStackTrace);
        // Linked HashMaps maintain the order the items are inserted. I am using it here so that the error JSON
        // produced from this class lists the attributes in the same order as other classes.
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("title",
            errorAttributes.get("error"));
        errorDetails.put("status",
            errorAttributes.get("status"));
        errorDetails.put("detail",
            errorAttributes.get("message"));
        errorDetails.put("timestamp",
            errorAttributes.get("timestamp"));
        errorDetails.put("developerMessage",
            "path: " + errorAttributes.get("path"));

        errorDetails.put("errors",
            helperFunctions.getConstraintViolation(this.getError(webRequest)));
        return errorDetails;
    }
}
