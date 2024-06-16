package de.telran.shop.service;

import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.dto.UsersDto;

import de.telran.shop.entity.Cart;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Orders;

import de.telran.shop.entity.Users;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.FavoritesRepository;
import de.telran.shop.repository.UsersRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class FavoritesServiceTest {

  @Mock
  private FavoritesRepository favoritesRepositoryMock;
  @Mock
  private UsersRepository usersRepositoryMock;
  @Mock
  private Mappers mappersMock;
  @InjectMocks
  private FavoritesService favoritesServiceMock;

  private Favorites favorites1;
  private Favorites favorites2;
  private Users users1;
  private Users users2;
  private FavoritesDto favoritesDto1;
  private FavoritesDto favoritesDto2;

  @BeforeEach
  void setUp() {
   users1 = new Users(
           1L,
           "Martin Watson",
           "marting@gmail.com",
           "+43920291",
           "PasswordOne",
           Role.CLIENT,
           new Cart(),
           new HashSet<Favorites>(),
           new HashSet<Orders>()
           );
    users2 = new Users(
           2L,
           "Steven Svenson",
           "steven@gmail.com",
           "+4350193",
           "PasswordTwo",
           Role.CLIENT,
           new Cart(),
           new HashSet<Favorites>(),
           new HashSet<Orders>()
   );
    favorites1 = new Favorites(
           1L,
           2L,
           users1);
   favorites2 = new Favorites(
           2L,
           2L,
           users2);
   favoritesDto1 = FavoritesDto.builder()
           .favoriteId(1L)
           .productId(2L)
           .users(UsersDto.builder()
                   .userId(1L)
                   .build())
           .build();
    favoritesDto2 = FavoritesDto.builder()
           .favoriteId(2L)
           .productId(2L)
           .users(UsersDto.builder()
                   .userId(2L)
                   .build())
           .build();
  }

  @Test
  void getFavorites() {
    when(favoritesRepositoryMock.findAll()).thenReturn(List.of(favorites1,favorites2));
    when(mappersMock.convertToFavoritesDto(favorites1)).thenReturn(favoritesDto1);
    when(mappersMock.convertToFavoritesDto(favorites2)).thenReturn(favoritesDto2);

    List<FavoritesDto> favorites = favoritesServiceMock.getFavorites();
    Assertions.assertTrue(favorites.size() > 0);

    verify(favoritesRepositoryMock, Mockito.times(1)).findAll();
    verify(mappersMock,Mockito.times(2)).convertToFavoritesDto(any(Favorites.class));
    Assertions.assertEquals(favoritesDto1.getFavoriteId(),favorites.get(0).getFavoriteId());
    Assertions.assertEquals(favoritesDto2.getFavoriteId(),favorites.get(1).getFavoriteId());
  }

  @Test
  void getFavoritesById() {
    Long id = 1L;
    when(favoritesRepositoryMock.findById(id)).thenReturn(Optional.of(favorites1));
    when(mappersMock.convertToFavoritesDto(favorites1)).thenReturn(favoritesDto1);

    FavoritesDto favoritesById = favoritesServiceMock.getFavoritesById(id);
    Assertions.assertNotNull(favoritesById);

    verify(favoritesRepositoryMock,Mockito.times(2)).findById(id);
    verify(mappersMock,Mockito.times(1)).convertToFavoritesDto(any(Favorites.class));
    Assertions.assertEquals(favoritesDto1.getFavoriteId(),favoritesById.getFavoriteId());
    Assertions.assertEquals(favorites1.getUsers().getUserId(),favoritesById.getUsers().getUserId());
  }

    @Test
    void deleteFavoritesById() {
      Long id = 1L;
      when(favoritesRepositoryMock.findById(id)).thenReturn(Optional.of(favorites1));
      favoritesServiceMock.deleteFavoritesById(id);
      verify(favoritesRepositoryMock,Mockito.times(1)).delete(favorites1);
    }

    @Test
    void insertFavorites() {
      Favorites favorites3 = new Favorites(
              3L,
              4L,
              users1);

      FavoritesDto favoritesDto3 = FavoritesDto.builder()
              .favoriteId(3L)
              .productId(4L)
              .users(UsersDto.builder()
                      .userId(1L)
                      .build())
              .build();

      when(usersRepositoryMock.findById(favoritesDto3.getUsers().getUserId())).thenReturn(Optional.of(users1));
      when(mappersMock.convertToFavoritesDto(favorites3)).thenReturn(favoritesDto3);
      when(favoritesRepositoryMock.save(any(Favorites.class))).thenReturn(favorites3);
      when(mappersMock.convertToFavorites(favoritesDto3)).thenReturn(favorites3);

      FavoritesDto favoritesDto = favoritesServiceMock.insertFavorites(favoritesDto3);

      Assertions.assertNotNull(favoritesDto);
      Assertions.assertNotNull(favoritesDto.getUsers());

      verify(usersRepositoryMock,Mockito.times(1)).findById(favorites3.getUsers().getUserId());
      verify(mappersMock,Mockito.times(1)).convertToFavoritesDto(any(Favorites.class));
      verify(mappersMock,Mockito.times(1)).convertToFavorites(any(FavoritesDto.class));
      verify(favoritesRepositoryMock,Mockito.times(1)).save(any(Favorites.class));

      Assertions.assertEquals(favoritesDto3.getUsers().getUserId(),favoritesDto.getUsers().getUserId());
      Assertions.assertEquals(favoritesDto3.getProductId(),favoritesDto.getProductId());
    }

    @Test
    void updateFavorites() {
      Long id = 1l;
      when(favoritesRepositoryMock.findById(id))
              .thenReturn(Optional.of(favorites1));
      when(usersRepositoryMock.findById(id)).thenReturn(Optional.of(users1));
      when(mappersMock.convertToFavorites(favoritesDto1)).thenReturn(favorites1);
      when(mappersMock.convertToFavoritesDto(favorites1)).thenReturn(favoritesDto1);
      when(favoritesRepositoryMock.save(any(Favorites.class))).thenReturn(favorites1);

      FavoritesDto favoritesDto = favoritesServiceMock.updateFavorites(favoritesDto1);
      Assertions.assertNotNull(favoritesDto);
      Assertions.assertNotNull(favoritesDto.getUsers().getUserId());
      verify(mappersMock,Mockito.times(1)).convertToFavoritesDto(any(Favorites.class));
      verify(mappersMock,Mockito.times(1)).convertToFavorites(any(FavoritesDto.class));
      verify(favoritesRepositoryMock,Mockito.times(1)).save(any(Favorites.class));
    }
}
