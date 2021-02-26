package com.lambdaschool.foundation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main class to start the application.
 */
@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = "file:/Users/lambdajohn/foundationconf.properties", ignoreResourceNotFound = true)
public class FoundationApplication
{
    /**
     * Connect to the system environment where environment variables live.
     */
    @Autowired
    private static Environment env;

    /**
     * If an environment variable is not found, set this to true
     */
    private static boolean stop = false;

    /**
     * If an application relies on an environment variable, check to make sure that environment variable is available!
     * If the environment variable is not available, you could set a default value, or as is done here, stop execution of the program
     *
     * @param envvar The system environment where environment variable live
     */
    private static void checkEnvironmentVariable(String envvar)
    {
        if (System.getenv(envvar) == null)
        {
            stop = true;
        }
    }

    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args)
    {
        // Check to see if the environment variables exists. If they do not, stop execution of application.
        checkEnvironmentVariable("OAUTHCLIENTID");
        checkEnvironmentVariable("OAUTHCLIENTSECRET");

        if (!stop)
        {
            SpringApplication.run(FoundationApplication.class,
                args);
        } else
        {
            System.out.println("Either or both the environment variables OAUTHCLIENTID, OAUTHCLIENTSECRET are not set. " +
                "They are required for this application to run");
        }
    }
}
