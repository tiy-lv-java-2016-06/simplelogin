package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.command.UserCommand;
import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleLoginApplicationTests {

	@Autowired
    WebApplicationContext wac;

    @Autowired
    UserRepository userRepository;

    MockMvc mockMvc;

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenTokenGoodInfoThenReturnToken() throws Exception {
        User user = new User("eddy", PasswordStorage.createHash("password"));
        userRepository.save(user);

        UserCommand userCommand = new UserCommand();
        userCommand.setUsername(user.getUsername());
        userCommand.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userCommand);


        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/token")
                .content(json)
                .contentType("application/json")
        ).andExpect(status().isOk()).andReturn();

        //Read out the value.  Use the object mapper to translate the json to a map
        HashMap<String, String> response = objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(user.getToken(), response.get("token"));
    }

    @Test
    public void fakeTest(){
        assertTrue(true);
    }

}
