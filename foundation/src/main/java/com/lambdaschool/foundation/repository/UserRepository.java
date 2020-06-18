package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.views.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The CRUD repository connecting User to the rest of the application
 */
public interface UserRepository
        extends CrudRepository<User, Long>
{
    /**
     * Find a user based off over username
     *
     * @param username the name (String) of user you seek
     * @return the first user object with the name you seek
     */
    User findByUsername(String username);

    /**
     * Find all users whose name contains a given substring ignoring case
     *
     * @param name the substring of the names (String) you seek
     * @return List of users whose name contain the given substring ignoring case
     */
    List<User> findByUsernameContainingIgnoreCase(String name);

    /**
     * Counts the number of user role combinations for the given userid and roleid. Answer should be only 0 or 1.
     *
     * @param userid The userid of the user of the user role combination to check
     * @param roleid The roleid of the role of the user role combination to check
     * @return A single number, a count
     */
    @Query(value = "SELECT COUNT(*) as count FROM userroles WHERE userid = :userid AND roleid = :roleid",
            nativeQuery = true)
    JustTheCount checkUserRolesCombo(
            long userid,
            long roleid);

    /**
     * Deletes the given user, role combination
     *
     * @param userid The user id of the user of this user role combination
     * @param roleid The role id of the role of this user role combination
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM UserRoles WHERE userid = :userid AND roleid = :roleid")
    void deleteUserRoles(
            long userid,
            long roleid);

    /**
     * Inserts the new user role combination
     *
     * @param uname  The username (String) of the user adding the record
     * @param userid The user id of the user of this user role combination
     * @param roleid The role id of the role of this user role combination
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO userroles(userid, roleid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:userid, :roleid, :uname, CURRENT_TIMESTAMP, :uname, CURRENT_TIMESTAMP)",
            nativeQuery = true)
    void insertUserRoles(
            String uname,
            long userid,
            long roleid);
}
