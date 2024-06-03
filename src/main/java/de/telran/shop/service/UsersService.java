package de.telran.shop.service;

import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Users;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<UsersDto> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();

        List<UsersDto> usersDtoList =
                usersList.stream()
                        .map(user -> UsersDto.builder()
                                .userId(user.getUserId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .phoneNumber(user.getPhoneNumber())
                                .passwordHash(user.getPasswordHash())
                                .role(user.getRole())
                                .build())
                        .collect(Collectors.toList());
        return usersDtoList;
    }

    public UsersDto getUserById(Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        UsersDto usersDto = null;
        if(users != null) {
            usersDto = new UsersDto(users.getUserId(),
                    users.getName(),
                    users.getEmail(),
                    users.getPhoneNumber(),
                    users.getPasswordHash(),
                    users.getRole());
        }
        return usersDto;
    }

    public void deleteUserById(Long id) {
        Users users = usersRepository.findById(id).orElse(null);
        if(users != null) {
            usersRepository.delete(users);
        }
    }

    public UsersDto insertUser(UsersDto usersDto) {

        Users users = new Users(0,
                usersDto.getName(),
                usersDto.getEmail(),
                usersDto.getPhoneNumber(),
                usersDto.getPasswordHash(),
                usersDto.getRole(),
                null,
                null,
                null);


        users = usersRepository.save(users);


        UsersDto responseUsersDto = new UsersDto(users.getUserId(),
                users.getName(),
                users.getEmail(),
                users.getPhoneNumber(),
                users.getPasswordHash(),
                users.getRole());

        return responseUsersDto;
    }

    public UsersDto updateUsers(UsersDto usersDto) {
        if(usersDto.getUserId()<=0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        Users users = usersRepository.findById(usersDto.getUserId()).orElse(null);
        if(users == null) {// Объект в БД не найден с таким Id, нужно вывести пользователю ошибку
            return null;
        }

        users.setName(usersDto.getName());
        users.setEmail(usersDto.getEmail());
        users.setPhoneNumber(usersDto.getPhoneNumber());
        users.setPasswordHash(usersDto.getPasswordHash());
        users.setRole(usersDto.getRole());

        users = usersRepository.save(users);

        UsersDto responseUsersDto = new UsersDto(users.getUserId(),
                users.getName(),
                users.getEmail(),
                users.getPhoneNumber(),
                users.getPasswordHash(),
                users.getRole());

        return responseUsersDto;
    }
}
