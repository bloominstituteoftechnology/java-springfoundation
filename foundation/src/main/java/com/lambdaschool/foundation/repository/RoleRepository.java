package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The CRUD Repository connecting Role to the rest of the application
 */
public interface RoleRepository
    extends CrudRepository<Role, Long>
{
    /**
     * JPA Query to find a role by name case insensitive search
     *
     * @param name the name of the role which you seek
     * @return the first role matching the given name using a case insensitive search
     */
    Role findByNameIgnoreCase(String name);

    /**
     * Updates the name of the role based on the given role id.
     *
     * @param uname  The username making this change
     * @param roleid The primary key (long) of the role to change
     * @param name   The new name (String) of the role
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET name = :name, lastmodifiedby = :uname, lastmodifieddate = CURRENT_TIMESTAMP WHERE roleid = :roleid",
        nativeQuery = true)
    void updateRoleName(
        String uname,
        long roleid,
        String name);

}
