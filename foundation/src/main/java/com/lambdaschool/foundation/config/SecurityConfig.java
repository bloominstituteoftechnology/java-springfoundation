package com.lambdaschool.foundation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * This class allows us to set standard security protocols to be used throughout the application,
 * such as password encoding, location of token store, our implementation of users, among others
 */
@Configuration
@EnableWebSecurity
// This allows us to further restrict access to an endpoint inside of a controller.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
    extends WebSecurityConfigurerAdapter
{
    /**
     * Allows us to customize the Authentication Manager. Normally, as we do here, we just take the defaults.
     *
     * @return The configured authentication manager
     * @throws Exception In case our custom configurations do not work.
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
        throws
        Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * Connects the user details used by Spring Security to our implementation of it
     */
    @Autowired
    private UserDetailsService securityUserService;

    /**
     * Ties our implementation of user details and password encoding to the Authentication Manager
     *
     * @param auth the connection to our authentication manager
     * @throws Exception in case our custom configuration does not work
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth)
        throws
        Exception
    {
        auth.userDetailsService(securityUserService)
            .passwordEncoder(encoder());
    }


    /**
     * We will store our token in memory. This way when the application exists all access tokens are invalidated.
     *
     * @return A token store configured to be used in memory
     */
    @Bean
    public TokenStore tokenStore()
    {
        return new InMemoryTokenStore();
    }

    /**
     * Set our preferred encoder for our password
     *
     * @return A password will be encoded using the standard BCrypt method
     */
    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}
