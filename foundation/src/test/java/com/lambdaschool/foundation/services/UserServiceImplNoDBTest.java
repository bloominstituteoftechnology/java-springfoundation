package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Role;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.models.UserRoles;
import com.lambdaschool.foundation.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoundationApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"})
public class UserServiceImplNoDBTest
{
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userrepos;

    @MockBean
    private RoleService roleService;

    @MockBean
    private HelperFunctions helperFunctions;

    private List<User> userList;


}