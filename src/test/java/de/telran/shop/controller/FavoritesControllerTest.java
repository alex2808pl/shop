package de.telran.shop.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.exceptions.FavoriteNotFoundException;
import de.telran.shop.exceptions.FavoriteWrongValueException;

import de.telran.shop.service.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoritesController.class)
class FavoritesControllerTest {
  
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FavoritesService favoritesServiceMock;
    private FavoritesDto favoritesDto1;
    private FavoritesDto favoritesDto2;
    @BeforeEach
    void setUp() {
       favoritesDto1 = FavoritesDto.builder()
               .favoriteId(1l)
               .productId(2l)
               .users(UsersDto.builder()
                       .userId(1l)
                       .build())
               .build();
        favoritesDto2 = FavoritesDto.builder()
                .favoriteId(2l)
                .productId(5l)
                .users(UsersDto.builder()
                        .userId(2l)
                        .build())
                .build();
    }

    @Test
    void getFavorites() throws Exception {

        when(favoritesServiceMock.getFavorites()).thenReturn(List.of(favoritesDto1,favoritesDto2));
        this.mockMvc.perform(get("/favorites"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..favoriteId").exists());
    }

    @Test
    void getFavoritesById() throws Exception {
        Long id = 1l;
        when(favoritesServiceMock.getFavoritesById(id)).thenReturn(favoritesDto1);
        this.mockMvc.perform(get("/favorites/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favoriteId").exists())
                .andExpect(jsonPath("$.favoriteId").value(1));

        when(favoritesServiceMock.getFavoritesById(id)).thenThrow(new FavoriteNotFoundException("the given favorite was not found"));
        this.mockMvc.perform(get("/favorites/{id}",id))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("the given favorite was not found"));
    }

    @Test
    void deleteFavoritesById() throws Exception {
        Long id = 1l;
        when(favoritesServiceMock.getFavoritesById(id)).thenReturn(favoritesDto1);
        this.mockMvc.perform(delete("/favorites/{id}",id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.favoriteId").doesNotExist());

        doThrow(new FavoriteNotFoundException("failed to delete favorite as it was not found")).when(favoritesServiceMock).deleteFavoritesById(id);
        this.mockMvc.perform(delete("/favorites/{id}",id))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void insertFavorites() throws Exception {
        favoritesDto2.setFavoriteId(0);
        when(favoritesServiceMock.insertFavorites(any(FavoritesDto.class))).thenReturn(favoritesDto1);
        this.mockMvc.perform(post("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoritesDto2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.favoriteId").value(1));

        favoritesDto2.setUsers(null);
        when(favoritesServiceMock.insertFavorites(any(FavoritesDto.class))).thenThrow(new FavoriteWrongValueException("failed to create favorite due to the wrong parameters"));
        this.mockMvc.perform(post("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoritesDto2)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateFavorites() throws Exception {
        favoritesDto1.setProductId(15);
        when(favoritesServiceMock.updateFavorites(any(FavoritesDto.class))).thenReturn(favoritesDto1);
        this.mockMvc.perform(put("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoritesDto1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favoriteId").value(1));

        favoritesDto1.setFavoriteId(0);
        when(favoritesServiceMock.updateFavorites(any(FavoritesDto.class))).thenThrow(new FavoriteWrongValueException("failed to update favorite`s data"));
        this.mockMvc.perform(put("/favorites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoritesDto1)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}