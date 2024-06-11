package de.telran.shop.service;

import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Users;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.FavoritesRepository;
import de.telran.shop.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceTest {

    @Mock
    private FavoritesRepository favoritesRepositoryMock;
    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private FavoritesService favoritesServiceTest;

    private FavoritesDto expectedFavoritesDto;
    private Favorites expectedFavorites;
    private Users testUsers;
    private UsersDto testUserDto;

    @BeforeEach
    void setUp() {

        testUsers =  new Users(1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        testUserDto = UsersDto.builder()
                .userId(1L)
                .build();
        expectedFavoritesDto = FavoritesDto.builder()
                .favoriteId(1L)
                .productId(1L)
                .users(testUserDto)
                .build();
        expectedFavorites = new Favorites(
                1L,
                1L,
                testUsers
        );
    }

    @Test
    void getFavoritesTest() {
        when(favoritesRepositoryMock.findAll()).thenReturn(List.of(expectedFavorites));
        when(mappersMock.convertToFavoritesDto(any(Favorites.class))).thenReturn(expectedFavoritesDto);
        assertEquals(List.of(expectedFavoritesDto),favoritesServiceTest.getFavorites());
    }

    @Test
    void getFavoritesByIdTest() {
        when(favoritesRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedFavorites));
        when(mappersMock.convertToFavoritesDto(any(Favorites.class))).thenReturn(expectedFavoritesDto);
        assertEquals(favoritesServiceTest.getFavoritesById(expectedFavorites.getFavoriteId()),expectedFavoritesDto);
        verify(mappersMock, times(1)).convertToFavoritesDto(any(Favorites.class));
    }

    @Test
    void deleteFavoritesByIdTest() {
        long id = 1L;
        favoritesServiceTest.deleteFavoritesById(id);
        verify(favoritesRepositoryMock,times(1)).deleteById(id);
    }

    @Test
    void insertFavoritesTest() {
        expectedFavoritesDto.setFavoriteId(0);
        when(usersRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testUsers));
        when(mappersMock.convertToFavorites(any(FavoritesDto.class))).thenReturn(expectedFavorites);
        when(favoritesRepositoryMock.save(any(Favorites.class))).thenReturn(expectedFavorites);
        when(mappersMock.convertToFavoritesDto(any(Favorites.class))).thenReturn(expectedFavoritesDto);

        assertEquals(favoritesServiceTest.insertFavorites(expectedFavoritesDto),expectedFavoritesDto);
        verify(mappersMock, times(1)).convertToFavoritesDto(any(Favorites.class));

    }

    @Test
    void updateFavoritesTest() {

    }
}