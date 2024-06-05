package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.Users;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.CartRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;

    public List<UsersDto> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        List<UsersDto> usersDtoList = MapperUtil.convertList(usersList, mappers::convertToUsersDto);

        return usersDtoList;
    }

    public UsersDto getUserById(Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        UsersDto usersDto = null;
        if (users != null) {
            usersDto = mappers.convertToUsersDto(users);
        }
        return usersDto;
    }


    public void deleteUserById(Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        if (users != null) {
            usersRepository.delete(users);
        }
    }


    public UsersDto insertUser(UsersDto usersDto) {
        Users users = mappers.convertToUsers(usersDto);
        users.setUserId(0);
        Users savedUsers = usersRepository.save(users);

        return mappers.convertToUsersDto(savedUsers);
    }


    public UsersDto updateUsers(UsersDto usersDto) {
        if (usersDto.getUserId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        Users users = usersRepository.findById(usersDto.getUserId()).orElse(null);
        if (users == null) {// Объект в БД не найден с таким Id, нужно вывести пользователю ошибку
            return null;
        }

        if (usersDto.getUserId() != users.getUserId()) {//номер users, введенный пользователем не совпадает с тем, который прописан в базе данных
            return null;
        }

        users = mappers.convertToUsers(usersDto);
        Users updatedUsers = usersRepository.save(users);
        UsersDto responseUsersDto = mappers.convertToUsersDto(updatedUsers);

        return responseUsersDto;
    }
}
