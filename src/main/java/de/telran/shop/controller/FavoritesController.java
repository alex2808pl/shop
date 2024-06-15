package de.telran.shop.controller;

import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.exceptions.FavoriteNotFoundException;
import de.telran.shop.exceptions.FavoriteWrongValueException;
import de.telran.shop.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoritesDto> getFavorites() {
        return favoritesService.getFavorites();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FavoritesDto getFavoritesById(@PathVariable Long id) {
        return favoritesService.getFavoritesById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavoritesById(@PathVariable Long id) {
        favoritesService.deleteFavoritesById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoritesDto insertFavorites(@RequestBody FavoritesDto favoritesDto) {
        return favoritesService.insertFavorites(favoritesDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public FavoritesDto updateFavorites(@RequestBody FavoritesDto favoritesDto) {
        return favoritesService.updateFavorites(favoritesDto);
    }

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<ErrorMessage> errorMessage(FavoriteNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(FavoriteWrongValueException.class)
    public ResponseEntity<ErrorMessage> errorMessage(FavoriteWrongValueException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

}
