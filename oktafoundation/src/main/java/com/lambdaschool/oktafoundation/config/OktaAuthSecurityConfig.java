package com.lambdaschool.oktafoundation.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class OktaAuthSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Bean
    // see https://www.devglan.com/spring-security/spring-boot-jwt-auth
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception
    {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/",
                "/h2-console/**",
                "/swagger-resources/**",
                "/swagger-resource/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/createnewuser")
            .permitAll()
            .antMatchers(HttpMethod.POST,
                "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.DELETE,
                "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.PUT,
                "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers("/users/**",
                "/useremails/**",
                "/oauth/revoke-token",
                "/logout")
            .authenticated()
            .antMatchers("/roles/**")
            .hasAnyRole("ADMIN")
            .and()
            .exceptionHandling()
            .and()
            .oauth2ResourceServer()
            .jwt();

        // process CORS annotations
        // http.cors();

        // disable the creation and use of Cross Site Request Forgery Tokens.
        // These tokens require coordination with the front end client that is beyond the scope of this class.
        // See https://www.yawintutor.com/how-to-enable-and-disable-csrf/ for more information
        http
            .csrf()
            .disable();

        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(http);

        // h2 console
        http.headers()
            .frameOptions()
            .disable();
    }
}