package de.telran.shop.controller;

import de.telran.shop.dto.UsersDto;
import de.telran.shop.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsersDto> getFavorites() {
        return usersService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsersDto getUserById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        usersService.deleteUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersDto insertUser(@RequestBody UsersDto usersDto) {
        return usersService.insertUser(usersDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersDto updateUsers(@RequestBody UsersDto usersDto) {
        return usersService.updateUsers(usersDto);
    }
}

