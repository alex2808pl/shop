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
    public List<UsersDto> getUsers() {
        return usersService.getUsers();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsersDto getUsersById(@PathVariable Long id) {
        return usersService.getUsersById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsersById(@PathVariable Long id) {
        usersService.deleteUsersById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsersDto insertUsers(@RequestBody UsersDto usersDto) {
        return usersService.insertUsers(usersDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersDto updateUsers(@RequestBody UsersDto usersDto) {
        return usersService.updateUsers(usersDto);
    }
}

