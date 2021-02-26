package com.lambdaschool.foundation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * This class enables and configures the Authorization Server. The class is also responsible for granting authorization to the client.
 * This class is responsible for generating and maintaining the access tokens.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig
    extends AuthorizationServerConfigurerAdapter
{
    /**
     * Client Id is the user name for the client application. It is read from the environment variable OAUTHCLIENTID
     */
    static final String CLIENT_ID = System.getenv("OAUTHCLIENTID");

    /**
     * Client secret is the password for the client application. It is read from the environment variable OAUTHCLIENTSECRET
     */
    static final String CLIENT_SECRET = System.getenv("OAUTHCLIENTSECRET"); // read from environment variable

    /**
     * We are using username and password to authenticate a user
     */
    static final String GRANT_TYPE_PASSWORD = "password";

    /**
     * We are using the client id and client security combination to authorize the client.
     * The client id and security can be base64 encoded into a single API key or code
     */
    static final String AUTHORIZATION_CODE = "authorization_code";

    /**
     * Scopes are meant to limit what a user can do with the application as a whole.
     * Here we allow the user to read from the application.
     * Currently we are not implementing scope in our applications. We are just setting up the framework to do so.
     */
    static final String SCOPE_READ = "read";

    /**
     * Scopes are meant to limit what a user can do with the application as a whole.
     * Here we allow the user to write to the application.
     * Currently we are not implementing scope in our applications. We are just setting up the framework to do so.
     */
    static final String SCOPE_WRITE = "write";

    /**
     * Scopes are meant to limit what a user can do with the application as a whole.
     * Here we say the user is trusted.
     * Currently we are not implementing scope in our applications. We are just setting up the framework to do so.
     */
    static final String TRUST = "trust";

    /**
     * Tells how long in seconds the access code should be kept valid. After this timeout, the user has to sign on again.
     * set to -1 if you want the token to be valid forever. 1 * 60 * 60 would give us 1 hour.
     */
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = -1;

    /**
     * The token store is configured in Security Config. However, the authorization server manages it
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * The authentication server authenticates a user to that user user gets assigned an access token that is managed by the authorization server
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * The authorization server must encrypt the client secret so needs to know what password encoder is in use.
     */
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Method to configure the Client Details Service for our application. This is created and managed by Spring.
     * We just need to give it our custom configuration.
     *
     * @param configurer The ClientDetailsServiceConfigurer used in our application. Spring Boot Security created this for us.
     *                   We just use it.
     * @throws Exception if the configuration fails
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer)
        throws
        Exception
    {
        configurer.inMemory()
            .withClient(CLIENT_ID)
            .secret(encoder.encode(CLIENT_SECRET))
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD,
                AUTHORIZATION_CODE)
            .scopes(SCOPE_READ,
                SCOPE_WRITE,
                TRUST)
            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    /**
     * Connects are endpoints to our custom authentication server and token store.
     * We can also rename the endpoints for certain oauth functions
     *
     * @param endpoints The Authorization Server Endpoints Configurer is created and managed by Spring Boot Security.
     *                  We give the configurer some custom configuration and let it work!
     * @throws Exception if the configuration fails
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        throws
        Exception
    {
        endpoints.tokenStore(tokenStore)
            .authenticationManager(authenticationManager);
        // here instead of our clients requesting authentication at the endpoint /oauth/token, they request it at the endpoint /login
        endpoints.pathMapping("/oauth/token",
            "/login");
    }
}