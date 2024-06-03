package de.telran.shop.service;

import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Users;
import de.telran.shop.repository.FavoritesRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UsersRepository usersRepository;

    public List<FavoritesDto> getFavorites() {
        List<Favorites> favoritesList = favoritesRepository.findAll();
        // преобразую пока вручную
        List<FavoritesDto> favoritesDtoList =
                favoritesList.stream()
                        .map(f -> FavoritesDto.builder()
                                .favoriteId(f.getFavoriteId())
                                .productId(f.getProductId())
                                .build())
                        .collect(Collectors.toList());
        return favoritesDtoList;
    }

    public FavoritesDto getFavoritesById(Long id) {
        Optional<Favorites> favorites = favoritesRepository.findById(id);
        FavoritesDto favoritesDto = null;
        if(favorites.isPresent()) {
             favoritesDto = new FavoritesDto(favorites.get().getFavoriteId(),
                    favorites.get().getProductId(), null);
        }
        return favoritesDto;
    }

    public void deleteFavoritesById(Long id) {
        Optional<Favorites> favorites = favoritesRepository.findById(id);
        if(favorites.isPresent()) {
            favoritesRepository.delete(favorites.get());
        }
    }

    public FavoritesDto insertFavorites(FavoritesDto favoritesDto) {
        // Получаю связанного Users
        UsersDto usersDto = favoritesDto.getUsers();
        Users users = null;
        if(usersDto!=null && usersDto.getUserId()!=null) {
            Optional<Users> usersOptional = usersRepository.findById(usersDto.getUserId());
            if(usersOptional.isPresent()) {
                users = usersOptional.get();
            }
         }

        // Преобразовую Dto в Entity
        Favorites favorites = new Favorites(0, favoritesDto.getProductId(), users);

        //Сохраняю в БД
        favorites = favoritesRepository.save(favorites);

        // трансформируем в Dto
        FavoritesDto responseFavoritesDto = new FavoritesDto(favorites.getFavoriteId(),
                favorites.getProductId(), null);

        return responseFavoritesDto;
    }

    public FavoritesDto updateFavorites(FavoritesDto favoritesDto) {
        if(favoritesDto.getFavoriteId()<=0) {
            // При редактировании такого быть не делжно, нужно вывести пользователю ошибку
            return null;
        }

        // Ищем такой объект в БД
        Optional<Favorites> favoritesOptional = favoritesRepository.findById(favoritesDto.getFavoriteId());
        if(!favoritesOptional.isPresent()) {
            // Объект в БД не найден с таким favoriteId, нужно вывести пользователю ошибку
            return null;
         }

        // Получаю связанного Users
        UsersDto usersDto = favoritesDto.getUsers();
        Users users = null;
        if(usersDto!=null && usersDto.getUserId()!=null) {
            Optional<Users> usersOptional = usersRepository.findById(usersDto.getUserId());
            if(usersOptional.isPresent()) {
                users = usersOptional.get();
            }
        }

        Favorites favorites  = favoritesOptional.get();
        favorites.setProductId(favoritesDto.getProductId());
        favorites.setUsers(users);

        //Сохраняю в БД
        favorites = favoritesRepository.save(favorites);

        // трансформируем в Dto
        FavoritesDto responseFavoritesDto = new FavoritesDto(favorites.getFavoriteId(),
                favorites.getProductId(), null);

        return responseFavoritesDto;
    }
}
