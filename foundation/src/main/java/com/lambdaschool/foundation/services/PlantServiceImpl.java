package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceFoundException;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Plants;
import com.lambdaschool.foundation.repository.RoleRepository;
import com.lambdaschool.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the PlantService Interface
 */
@Transactional
@Service(value = "roleService")
public class PlantServiceImpl
    implements PlantService
{
    /**
     * Connects this service to the Plants Model
     */
    @Autowired
    RoleRepository rolerepos;

    /**
     * Connect this service to the User Model
     */
    @Autowired
    UserRepository userrepos;

    /**
     * Connects this service to the auditing service in order to get current user name
     */
    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Plants> findAll()
    {
        List<Plants> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        rolerepos.findAll()
            .iterator()
            .forEachRemaining(list::add);
        return list;
    }


    @Override
    public Plants findRoleById(long id)
    {
        return rolerepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Plants id " + id + " not found!"));
    }

    @Override
    public Plants findByName(String name)
    {
        Plants rr = rolerepos.findByNameIgnoreCase(name);

        if (rr != null)
        {
            return rr;
        } else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public Plants save(Plants plants)
    {
        if (plants.getUsers()
            .size() > 0)
        {
            throw new ResourceFoundException("User Roles are not updated through Plants.");
        }

        return rolerepos.save(plants);
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        rolerepos.deleteAll();
    }

    @Transactional
    @Override
    public Plants update(
        long id,
        Plants plants)
    {
        if (plants.getName() == null)
        {
            throw new ResourceNotFoundException("No plants name found to update!");
        }

        if (plants.getUsers()
            .size() > 0)
        {
            throw new ResourceFoundException("User Roles are not updated through Plants. See endpoint POST: users/user/{userid}/plants/{roleid}");
        }

        Plants newPlants = findRoleById(id); // see if id exists

        rolerepos.updateRoleName(userAuditing.getCurrentAuditor()
                .get(),
            id,
            plants.getName());
        return findRoleById(id);
    }
}
