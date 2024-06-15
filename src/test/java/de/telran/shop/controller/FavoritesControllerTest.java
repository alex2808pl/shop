package de.telran.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.service.CartService;
import de.telran.shop.service.FavoritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoritesController.class)
class FavoritesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FavoritesService favoritesServiceMock;

    private FavoritesDto favoritesExpected1, favoritesExpected2;
    @BeforeEach
    void setUp() {
        favoritesExpected1 = FavoritesDto.builder()
                .favoriteId(1L)
                .users(null)
                .productId(1L)
                .build();
        favoritesExpected2 = FavoritesDto.builder()
                .favoriteId(2L)
                .users(null)
                .productId(1L)
                .build();
    }

    @Test
    void getFavorites() throws Exception {
        when(favoritesServiceMock.getFavorites()).thenReturn(List.of(favoritesExpected1,favoritesExpected2));
        this.mockMvc.perform(get("/favorites")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..favoriteId").exists());
    }

    @Test
    void getFavoritesById() throws Exception {
            when(favoritesServiceMock.getFavoritesById(anyLong())).thenReturn(favoritesExpected1);
            this.mockMvc.perform(get("/favorites/{id}",1)).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.favoriteId").exists())
                    .andExpect(jsonPath("$.favoriteId").value(1));
    }

    @Test
    void deleteFavoritesById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/favorites/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.favoriteId").doesNotExist());
    }

    @Test
    void insertFavorites()  throws Exception {
        favoritesExpected2.setFavoriteId(0L);
        when(favoritesServiceMock.insertFavorites(any(FavoritesDto.class))).thenReturn(favoritesExpected1);
        this.mockMvc.perform(post("/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoritesExpected2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.favoriteId").exists())
                .andExpect(jsonPath("$.favoriteId").value(1));
    }

    @Test
    void updateFavorites() throws Exception {
        favoritesExpected2.setFavoriteId(1L);
        when(favoritesServiceMock.updateFavorites(any(FavoritesDto.class))).thenReturn(favoritesExpected1);
        this.mockMvc.perform(put("/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoritesExpected2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favoriteId").exists())
                .andExpect(jsonPath("$.favoriteId").value(1));
    }
}