package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.entity.Favorites;
import de.telran.shop.exceptions.FavoriteNotFoundException;
import de.telran.shop.exceptions.FavoriteWrongValueException;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.FavoritesRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UsersRepository usersRepository;
    private final Mappers mappers;
    private final ModelMapper modelMapper;


    public List<FavoritesDto> getFavorites() {
        return MapperUtil.convertList(favoritesRepository.findAll(), mappers::convertToFavoritesDto);
    }

    public FavoritesDto getFavoritesById(Long id) {
        if(!favoritesRepository.findById(id).isPresent()) {
            throw new FavoriteNotFoundException("the given favorite was not found");
        }
        return mappers.convertToFavoritesDto(favoritesRepository.findById(id).orElse(null));
    }

    public void deleteFavoritesById(Long id) {
        if(!favoritesRepository.findById(id).isPresent()) {
            throw new FavoriteNotFoundException("failed to delete favorite as it was not found");
        }
        favoritesRepository.findById(id).ifPresent(favoritesRepository::delete);
    }

    public FavoritesDto insertFavorites(FavoritesDto favoritesDto) {
        if (favoritesDto.getUsers() != null
                &&  usersRepository.findById(favoritesDto.getUsers().getUserId()).orElse(null) != null) {
            return mappers.convertToFavoritesDto(favoritesRepository.save(mappers.convertToFavorites(favoritesDto)));
        }
        throw new FavoriteWrongValueException("failed to create favorite due to the wrong parameters");
    }

    public FavoritesDto updateFavorites(FavoritesDto favoritesDto) {
        if(favoritesDto.getFavoriteId() > 0
                && favoritesRepository.findById(favoritesDto.getFavoriteId()).orElse(null) != null
                && usersRepository.findById(favoritesDto.getUsers().getUserId()).orElse(null) != null) {
                        return mappers.convertToFavoritesDto(favoritesRepository.save(mappers.convertToFavorites(favoritesDto)));
        }
        throw new FavoriteWrongValueException("failed to update favorite`s data");
    }
}