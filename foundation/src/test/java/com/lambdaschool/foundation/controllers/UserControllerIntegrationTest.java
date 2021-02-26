package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.FoundationApplicationTesting;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithUserDetails("admin")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = FoundationApplicationTesting.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception
    {
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
    public void whenMeasuredResponseTime() throws
                                           Exception
    {
        long time = System.currentTimeMillis();
        this.mockMvc.perform(get("/users/users"))
            .andDo(print());
        long responseTime = (System.currentTimeMillis() - time);

        assertTrue("timestamp",
            (responseTime < 5000L));
    }

    @Test
    public void getAllUsers() throws
                              Exception
    {
        this.mockMvc.perform(get("/users/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("cinnamon")));
    }

    @Test
    public void getUserLikeName() throws
                                  Exception
    {
        this.mockMvc.perform(get("/users/user/name/like/{userName}",
            "kitty"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("misskitty")));
    }

    @Test
    public void getUserById() throws
                              Exception
    {
        this.mockMvc.perform(get("/users/user/{userid}",
            4))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("admin")));
    }

    @Test
    public void getUserByIdNotFound() throws
                                      Exception
    {
        this.mockMvc.perform(get("/users/user/{userid}",
            100))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("ResourceNotFoundException")));
    }

    @Test
    public void getUserByName() throws
                                Exception
    {
        this.mockMvc.perform(get("/users/user/name/{userName}",
            "admin"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("admin")));
    }

    @Test
    public void getUserByNameNotFound() throws
                                        Exception
    {
        this.mockMvc.perform(get("/users/user/name/{userName}",
            "rabbit"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("ResourceNotFoundException")));
    }

    @Test
    public void givenPostAUser() throws
                                 Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/user")
            .content("{\"username\": \"Ginger\", \"password\": \"EATEATEAT\", \"primaryemail\" : \"ginger@home.local\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.header()
                .exists("location"));
    }

    @Test
    public void givenPutAUser() throws
                                Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/user/11")
            .content("{\"username\": \"stumps\", \"password\": \"EATEATEAT\", \"primaryemail\" : \"stumps@home.local\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void deleteUserById() throws
                                 Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/user/{id}",
            13))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteUserByIdNotFound() throws
                                         Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/user/{id}",
            100))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void UpdateUser() throws
                             Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/user/{userid}",
            7)
            .content("{\"password\": \"EATEATEAT\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void getCurrentUserInfo() throws Exception
    {
        this.mockMvc.perform(get("/users/getuserinfo"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("admin")));
    }
}