package com.mengzhou.springbootrestapi.user;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Test
    public void testGetUsers() throws Exception {
        // Mocking the repository response
        List<User> users = new ArrayList<>();
        users.add(new User(1, "meng", LocalDate.now().minusYears(15)));
        users.add(new User(2, "david", LocalDate.now().minusYears(10)));

        when(repository.findAll()).thenReturn(users);

        // Sending the GET request and verifying the response
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("meng"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("david"));

        // Verifying the repository method call
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a sample user to send in the request body
        User newUser = new User(1, "meng", LocalDate.now().minusYears(15));

        // Mock the repository save method and capture the argument
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(repository.save(captor.capture())).thenReturn(newUser);

        // Send the POST request with the user JSON in the request body
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"meng\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Verify the repository save method was called with the correct user
        verify(repository, times(1)).save(captor.getValue());
        User savedUser = captor.getValue();
        assertEquals("meng", savedUser.getName());

    }

    @Test
    public void testDeleteUser() throws Exception {

        Integer userId = 123;  

        doNothing().when(repository).deleteById(userId);

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk());

        verify(repository).deleteById(userId);

    }
}
