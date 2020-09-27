package com.lambdaschool.foundation.config;

import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.UserRepository;
import com.lambdaschool.foundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    // A very specific implementation of userrepos.findByUserName was needed for this filter,
    // so the repository is used directly.
    @Autowired
    UserRepository userrepos;

    // Using the service is preferred so for save, the service is used
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain) throws ServletException, IOException
    {
        // find the username of the authenticated user
        Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        if (userrepos.findByUsername(authentication.getName()) == null)
        {
            User newUser = new User(authentication.getName());
            userService.save(newUser);

        } else
        {
            // we already have this user so nothing to update
        }

        // continue the filter chain.
        filterChain.doFilter(httpServletRequest,
            httpServletResponse);
    }
}
