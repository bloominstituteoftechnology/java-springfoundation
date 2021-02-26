package com.lambdaschool.foundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for UserRoles
 */
@Embeddable
public class UserRolesId
    implements Serializable
{
    /**
     * The id of the user
     */
    private long user;

    /**
     * The id of the role
     */
    private long role;

    /**
     * The default constructor required by JPA
     */
    public UserRolesId()
    {
    }

    /**
     * Getter for the user id
     *
     * @return long the user id
     */
    public long getUser()
    {
        return user;
    }

    /**
     * Setter for the user id
     *
     * @param user the new user id for this object
     */
    public void setUser(long user)
    {
        this.user = user;
    }

    /**
     * Getter for the role id
     *
     * @return long the role id
     */
    public long getRole()
    {
        return role;
    }

    /**
     * The setter for the role id
     *
     * @param role the new role id for this object
     */
    public void setRole(long role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        UserRolesId that = (UserRolesId) o;
        return user == that.user &&
            role == that.role;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
