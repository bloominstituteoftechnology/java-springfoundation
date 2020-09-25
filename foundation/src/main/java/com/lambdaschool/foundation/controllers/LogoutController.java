package com.lambdaschool.foundation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * The application implements its own logout endpoint. This works by removing the authentication token for the user.
 */
@Controller
public class LogoutController
{
    /**
     * Connect to the Token store so the application can remove the token
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * Removes the token for the signed on user. The signed user will lose access to the application. They would have to sign on again.
     *
     * <br>Example: <a href="http://localhost:2019/logout">http://localhost:2019/logout</a>
     *
     * @param request the Http request from which we find the authorization header which includes the token to be removed
     */
    // yes, both endpoints are mapped to the same Java method! So, either one will work.
    @GetMapping(value = {"/oauth/revoke-token", "/logout"},
        produces = "application/json")
    public ResponseEntity<?> logoutSelf(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null)
        {
            // find the token
            String tokenValue = authHeader.replace("Bearer",
                "")
                .trim();
            // and remove it!
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
