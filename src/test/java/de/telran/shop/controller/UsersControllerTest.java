package de.telran.shop.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService usersServiceMock;

    private UsersDto usersDto1;
    private UsersDto usersDto2;
    private UsersDto usersDto3;
    private UsersDto usersDto4;

    @BeforeEach
    void setUp() {
        usersDto1 = UsersDto.builder()
                .userId(1L)
                .name("Mary Anderson")
                .email("maryanderson@example.com")
                .phoneNumber("+491583265487")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void testGetUsers() throws Exception {

        usersDto2 = UsersDto.builder()
                .userId(2L)
                .name("Jane Green")
                .email("janegreen@example.com")
                .phoneNumber("+491583265482")
                .passwordHash("PasswordTwo")
                .role(Role.CLIENT)
                .build();

        when(usersServiceMock.getUsers()).thenReturn(List.of(usersDto1, usersDto2));
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$..name").exists());
    }

    @Test
    void testGetUserById() throws Exception {
        Long id = 1L;
        when(usersServiceMock.getUsersById(id)).thenReturn(usersDto1);
        mockMvc.perform(get("/users/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void testDeleteUserById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/users/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.userId").doesNotExist())
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void testInsertUser() throws Exception {
        usersDto3 = UsersDto.builder()
                .name("Mary Anderson")
                .email("maryanderson@example.com")
                .phoneNumber("+491583265487")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();

        when(usersServiceMock.insertUsers(any(UsersDto.class))).thenReturn(usersDto1);
        String requestBody = objectMapper.writeValueAsString(usersDto3);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value(usersDto1.getName()));
    }

    @Test
    void testUpdateUsers() throws Exception {
        usersDto4 = UsersDto.builder()
                .userId(1L)
                .name("Mary Grey")
                .email("marygrey@example.com")
                .phoneNumber("+491586593216")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();

        when(usersServiceMock.updateUsers(any(UsersDto.class))).thenReturn(usersDto4);
        String requestBody = objectMapper.writeValueAsString(usersDto1);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value(usersDto4.getName()))
                .andExpect(jsonPath("$.email").value(usersDto4.getEmail()));
    }
}