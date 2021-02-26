package com.lambdaschool.foundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.foundation.FoundationApplicationTesting;
import com.lambdaschool.foundation.models.Role;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.models.UserRoles;
import com.lambdaschool.foundation.models.Useremail;
import com.lambdaschool.foundation.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 Update method requires an authenticated user so must Mock up a user
 */

@RunWith(SpringRunner.class)
@WithMockUser(username = "admin",
    roles = {"USER", "ADMIN"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
public class UserControllerUnitNoDBTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("admin",
            "ILuvM4th!",
            "admin@lambdaschool.test");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));

        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@email.test"));
        u1.getUseremails()
            .get(0)
            .setUseremailid(10);

        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@mymail.test"));
        u1.getUseremails()
            .get(1)
            .setUseremailid(11);

        u1.setUserid(101);
        userList.add(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("cinnamon",
            "1234567",
            "cinnamon@lambdaschool.test");
        u1.getRoles()
            .add(new UserRoles(u2,
                r2));
        u1.getRoles()
            .add(new UserRoles(u2,
                r3));

        u2.getUseremails()
            .add(new Useremail(u2,
                "cinnamon@mymail.test"));
        u2.getUseremails()
            .get(0)
            .setUseremailid(20);

        u2.getUseremails()
            .add(new Useremail(u2,
                "hops@mymail.test"));
        u2.getUseremails()
            .get(1)
            .setUseremailid(21);

        u2.getUseremails()
            .add(new Useremail(u2,
                "bunny@email.test"));
        u2.getUseremails()
            .get(2)
            .setUseremailid(22);

        u2.setUserid(102);
        userList.add(u2);

        // user
        User u3 = new User("testingbarn",
            "ILuvM4th!",
            "testingbarn@school.lambda");
        u3.getRoles()
            .add(new UserRoles(u3,
                r1));

        u3.getUseremails()
            .add(new Useremail(u3,
                "barnbarn@email.test"));
        u3.getUseremails()
            .get(0)
            .setUseremailid(30);

        u3.setUserid(103);
        userList.add(u3);

        User u4 = new User("testingcat",
            "password",
            "testingcat@school.lambda");
        u4.getRoles()
            .add(new UserRoles(u4,
                r2));

        u4.setUserid(104);
        userList.add(u4);

        User u5 = new User("testingdog",
            "password",
            "testingdog@school.lambda");
        u4.getRoles()
            .add(new UserRoles(u5,
                r2));

        u5.setUserid(105);
        userList.add(u5);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws
                               Exception
    {
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll())
            .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void listUsersNameContaining() throws
                                          Exception
    {
        String apiUrl = "/users/user/name/like/cin";

        Mockito.when(userService.findByNameContaining(any(String.class)))
            .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getUserById() throws
                              Exception
    {
        String apiUrl = "/users/user/12";

        Mockito.when(userService.findUserById(12))
            .thenReturn(userList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getUserByIdNotFound() throws
                                      Exception
    {
        String apiUrl = "/users/user/77";

        Mockito.when(userService.findUserById(77))
            .thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void getUserByName() throws
                                Exception
    {
        String apiUrl = "/users/user/name/testing";

        Mockito.when(userService.findByName("testing"))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }

    @Test
    public void addNewUser() throws
                             Exception
    {
        String apiUrl = "/users/user";

        Mockito.when(userService.save(any(User.class)))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"tiger\", \"password\": \"ILuvM4th!\", \"primaryemail\" : \"tiger@home.local\"}");

        mockMvc.perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws
                             Exception
    {
        String apiUrl = "/users/user/{userid}";

        Mockito.when(userService.update(any(User.class),
            any(Long.class)))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl,
            100L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"tigerUpdated\", \"password\": \"EATEATEAT\", \"primaryemail\" : \"ginger@home.local\"}");

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUserPatch() throws
                                  Exception
    {
        String apiUrl = "/users/user/{userid}";

        Mockito.when(userService.update(any(User.class),
            any(Long.class)))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl,
            100L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"tigerUpdated\", \"password\": \"EATEATEAT\", \"primaryemail\" : \"ginger@home.local\"}");

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserById() throws
                                 Exception
    {
        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
            "3")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCurrentUserInfo() throws Exception
    {
        String apiUrl = "/users/getuserinfo";

        Mockito.when(userService.findByName(anyString()))
            .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List",
            er,
            tr);
    }
}