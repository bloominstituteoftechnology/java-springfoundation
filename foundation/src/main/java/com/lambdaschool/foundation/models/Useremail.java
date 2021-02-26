package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * The entity allowing interaction with the useremails table
 * <p>
 * requires each combination of user and useremail to be unique. The same email cannot be assigned to the same user more than once.
 */
@Entity
@Table(name = "useremails")
public class Useremail
    extends Auditable
{
    /**
     * The primary key (long) of the useremails table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long useremailid;

    /**
     * Email (String) for this user. Cannot be nullable.
     * Must be in the format userid@domain.upperLevelDomain
     */
    @Column(nullable = false)
    @Email
    private String useremail;

    /**
     * The userid of the user assigned to this email is what is stored in the database.
     * This is the entire user object!
     * <p>
     * Forms a Many to One relationship between useremails and users.
     * A user can have many emails.
     */
    @ManyToOne
    @JoinColumn(name = "userid",
        nullable = false)
    @JsonIgnoreProperties(value = "useremails",
        allowSetters = true)
    private User user;

    /**
     * The default controller is required by JPA
     */
    public Useremail()
    {
    }

    /**
     * Given the parameters, create a new useremail object
     *
     * @param user      the user (User) assigned to the email
     * @param useremail useremail (String) for the given user
     */
    public Useremail(
        User user,
        String useremail)
    {
        this.useremail = useremail;
        this.user = user;
    }

    /**
     * Getter for useremailid
     *
     * @return the primary key (long) of this useremail object
     */
    public long getUseremailid()
    {
        return useremailid;
    }

    /**
     * Setter for useremailid. Used for seeding data
     *
     * @param useremailid the new primary key (long) of this useremail object
     */
    public void setUseremailid(long useremailid)
    {
        this.useremailid = useremailid;
    }

    /**
     * Getter for useremail
     *
     * @return the email (String) associated with this useremail object in lowercase
     */
    public String getUseremail()
    {
        return useremail;
    }

    /**
     * Setter for useremail
     *
     * @param useremail the email (String) to replace the one currently assigned to this useremail object, in lowercase
     */
    public void setUseremail(String useremail)
    {
        this.useremail = useremail.toLowerCase();
    }


    /**
     * Getter for user
     *
     * @return the user object associated with this useremail.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Setter for user
     *
     * @param user the user object to replace the one currently assigned to this useremail object
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}
