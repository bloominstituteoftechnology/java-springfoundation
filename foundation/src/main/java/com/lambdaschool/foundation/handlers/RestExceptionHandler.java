package com.lambdaschool.foundation.handlers;

import com.lambdaschool.foundation.exceptions.ResourceFoundException;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.ErrorDetail;
import com.lambdaschool.foundation.services.HelperFunctions;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Date;

/**
 * This is the driving class when an exception occurs. All exceptions are handled here.
 * This class is shared across all controllers due to the annotation RestControllerAdvice;
 * this class gives advice to all controllers on how to handle exceptions.
 * Due to the annotation Order(Ordered.HIGHEST_PRECEDENCE), this class takes precedence over all other controller advisors.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler
    extends ResponseEntityExceptionHandler
{
    /**
     * Connects this class with the Helper Functions
     */
    @Autowired
    private HelperFunctions helperFunctions;

    /**
     * The constructor for the RestExceptionHandler. Currently we do not do anything special. We just call the parent constructor.
     */
    public RestExceptionHandler()
    {
        super();
    }

    /**
     * Our custom handling of ResourceNotFoundExceptions. This gets thrown manually by our application.
     *
     * @param rnfe All the information about the exception that is thrown.
     * @return The error details for displaying to the client plus the status Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(rnfe));

        return new ResponseEntity<>(errorDetail,
            null,
            HttpStatus.NOT_FOUND);
    }

    /**
     * Our custom handling of ResourceFoundExceptions. This gets thrown manually by our application.
     *
     * @param rfe All the information about the exception that is thrown.
     * @return The error details for displaying to the client plus the status Bad Request.
     */
    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<?> handleResourceFoundException(ResourceFoundException rfe)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Unexpected Resource");
        errorDetail.setDetail(rfe.getMessage());
        errorDetail.setDeveloperMessage(rfe.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(rfe));

        return new ResponseEntity<>(errorDetail,
            null,
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OAuth2AccessDeniedException.class)
    public ResponseEntity<?> handleOAuth2AccessDeniedException(OAuth2AccessDeniedException ex)
    {
        return new ResponseEntity<>(ex,
            null,
            HttpStatus.resolve(ex.getHttpErrorCode()));
    }

    /**
     * All other exceptions not handled elsewhere are handled by this method.
     *
     * @param ex      The actual exception used to get error messages
     * @param body    The body of this request. Not used in this method.
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client. Not used in this method.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex,
        Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Rest Internal Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /*********************
     * The rest of the methods are not required and so are provided for reference only.
     * They allow you to better customized exception messages
     ********************/

    /**
     * Reports when a correct endpoint is accessed but with an unsupported Http Method.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Incorrect method: " + ex.getMethod());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Methods are: " + Arrays.toString(ex.getSupportedMethods()));
        errorDetail.setDeveloperMessage("HTTP Method Not Valid for Endpoint (check for valid URI and proper HTTP Method)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Reports when a correct endpoint is accessed but with an unsupported content, media, type.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Incorrect content type: " + ex.getContentType());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Content / Media Types are: " + ex.getSupportedMediaTypes());
        errorDetail.setDeveloperMessage("Content / Media Type Not Valid for Endpoint (check for valid URI and proper content / media type)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Reports when a correct endpoint is accessed but with an unacceptable content, media, type.
     * An unacceptable content, media type is one that server does not support at all, regardless of endpoints.
     * Normally the error is unsupported type, so this rarely happens.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
        HttpMediaTypeNotAcceptableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Unacceptable content type: " + ex.getMessage());
        errorDetail.setDetail("Path: " + request.getDescription(false) + " | Supported Content / Media Types are: " + ex.getSupportedMediaTypes());
        errorDetail.setDeveloperMessage("Content / Media Type Not Valid for Endpoint (check for valid URI and proper content / media type)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * If a path variable is missing, however, in this application this normally becomes an unhandled endpoint
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
        MissingPathVariableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle(ex.getVariableName() + " Missing Path Variable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * A parameter is missing.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Parameter Missing for " + "Path: " + request.getDescription(false));
        errorDetail.setDetail("Parameter Missing: " + ex.getParameterName() + " Type: " + ex.getParameterType());
        errorDetail.setDeveloperMessage(ex.getMessage() + " " + ex.getClass());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Servlet Request Binding Exception.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
        ServletRequestBindingException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Request Binding Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Conversion Not Supported.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
        ConversionNotSupportedException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Conversion Not Support");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Type Mismatch.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
        TypeMismatchException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Type Mismatch");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Message Not Readable.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Message Not Writable.
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
        HttpMessageNotWritableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Path: " + request.getDescription(false) + " Message Not Writable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName() + " " + ex.getMostSpecificCause());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * A when an argument fails the @Valid check
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Method Argument Not Valid");
        errorDetail.setDetail(request.getDescription(false) + " | parameter: " + ex.getParameter());
        errorDetail.setDeveloperMessage(ex.getBindingResult()
            .toString());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Missing Servlet Request
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
        MissingServletRequestPartException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle(request.getDescription(false) + " Missing Servlet Request");
        errorDetail.setDetail("Request Part Name: " + ex.getRequestPartName() + " | " + ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * Bind Exception
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleBindException(
        BindException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Bind Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
            .getName() + " " + ex.getBindingResult());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }


    /**
     * Client is trying to access an endpoint that does not exist. Requires additions to the application.properties file.
     * server.error.whitelabel.enabled=false
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     *
     * @param ex      The actual exception used to get error messages
     * @param headers Headers that are involved in this request. Not used in this method.
     * @param status  The Http Status generated by the exception. Forwarded to the client.
     * @param request The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Rest Endpoint Not Valid");
        errorDetail.setDetail(request.getDescription(false));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }

    /**
     * An async request has timed out
     *
     * @param ex         The actual exception used to get error messages
     * @param headers    Headers that are involved in this request. Not used in this method.
     * @param status     The Http Status generated by the exception. Forwarded to the client.
     * @param webRequest The request that was made by the client.
     * @return The error details to display to the client plus the status that from the exception.
     */
    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
        AsyncRequestTimeoutException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest webRequest)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Async Request Timeout Error");
        errorDetail.setDetail("path: " + webRequest.getDescription(false));
        errorDetail.setDeveloperMessage(ex.getMessage());
        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(errorDetail,
            null,
            status);
    }
}
