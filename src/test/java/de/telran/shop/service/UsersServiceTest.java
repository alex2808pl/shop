package de.telran.shop.service;

import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private UsersService usersServiceMock;

    private UsersDto usersDto1;
    private UsersDto usersDto2;
    private UsersDto usersDto3;
    private UsersDto usersDto4;

    private Users users1;
    private Users users2;
    private Users users3;
    private Users users4;

    @BeforeEach
    void setUp() {

        users1 = new Users(
                1L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        usersDto1 = UsersDto.builder()
                .userId(1L)
                .name("Mary Anderson")
                .email("maryanderson@example.com")
                .phoneNumber("+491583265487")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();
    }


    @Test
    void testGetUsers() {

        users2 = new Users(
                1L,
                "Jane Green",
                "janegreen@example.com",
                "+49153215694",
                "PasswordTwo",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        usersDto2 = UsersDto.builder()
                .userId(2L)
                .name("Jane Green")
                .email("janegreen@example.com")
                .phoneNumber("+491583265482")
                .passwordHash("PasswordTwo")
                .role(Role.CLIENT)
                .build();

        when(usersRepositoryMock.findAll()).thenReturn(List.of(users1, users2));
        when(mappersMock.convertToUsersDto(users1)).thenReturn(usersDto1);
        when(mappersMock.convertToUsersDto(users2)).thenReturn(usersDto2);

        List<UsersDto> actualUsersDtoList = usersServiceMock.getUsers();

        assertTrue(actualUsersDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToUsersDto(any(Users.class));
        verify(usersRepositoryMock, times(1)).findAll();
        assertEquals(users1.getUserId(), actualUsersDtoList.get(0).getUserId());
    }

    @Test
    void testGetUsersById() {
        Long id = 1l;
        when(usersRepositoryMock.findById(id)).thenReturn(Optional.of(users1));
        when(mappersMock.convertToUsersDto(users1)).thenReturn(usersDto1);

        UsersDto actualUsersDto = usersServiceMock.getUsersById(id);

        assertNotNull(usersDto1);
        verify(mappersMock, times(1)).convertToUsersDto(any(Users.class));
        verify(usersRepositoryMock, times(1)).findById(id);
        assertEquals(users1.getUserId(), actualUsersDto.getUserId());
    }

    @Test
    void testDeleteUsersById() {
        Long id = 1l;
        when(usersRepositoryMock.findById(id)).thenReturn(Optional.of(users1));

        usersServiceMock.deleteUsersById(id);

        assertNull(usersServiceMock.getUsersById(id));
        verify(usersRepositoryMock, times(1)).delete(users1);
    }

    @Test
    void testInsertUsers() {

        usersDto3 = UsersDto.builder()
                .name("Mary Anderson")
                .email("maryanderson@example.com")
                .phoneNumber("+491583265487")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();

        users3 = new Users(
                0L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        when(mappersMock.convertToUsers(usersDto3)).thenReturn(users3);
        when(usersRepositoryMock.save(any(Users.class))).thenReturn(users1);
        when(mappersMock.convertToUsersDto(users1)).thenReturn(usersDto1);

        UsersDto actualUsersDto = usersServiceMock.insertUsers(usersDto3);

        assertNotNull(actualUsersDto);
        assertNotNull(actualUsersDto.getUserId());
        verify(mappersMock, times(1)).convertToUsers(any(UsersDto.class));
        verify(mappersMock, times(1)).convertToUsersDto(any(Users.class));
        verify(usersRepositoryMock, times(1)).save(any(Users.class));
        assertEquals(usersDto3.getName(), actualUsersDto.getName());
        assertEquals(usersDto3.getPasswordHash(), actualUsersDto.getPasswordHash());
    }

    @Test
    void testUpdateUsers() {

        usersDto4 = UsersDto.builder()
                .userId(1L)
                .name("Mary Grey")
                .email("marygrey@example.com")
                .phoneNumber("+491583265487")
                .passwordHash("PasswordOne")
                .role(Role.CLIENT)
                .build();

        users4 = new Users(
                1L,
                "Mary Grey",
                "marygrey@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        when(usersRepositoryMock.findById(usersDto4.getUserId())).thenReturn(Optional.of(users1));
        when(mappersMock.convertToUsers(usersDto4)).thenReturn(users4);
        when(usersRepositoryMock.save(any(Users.class))).thenReturn(users4);
        when(mappersMock.convertToUsersDto(users4)).thenReturn(usersDto4);

        UsersDto actualUsersDto = usersServiceMock.updateUsers(usersDto4);

        assertNotNull(actualUsersDto);
        assertNotNull(actualUsersDto.getUserId());

        verify(mappersMock, times(1)).convertToUsers(any(UsersDto.class));
        verify(usersRepositoryMock, times(1)).save(any(Users.class));
        verify(mappersMock, times(1)).convertToUsersDto(any(Users.class));

        assertEquals(usersDto4.getUserId(), actualUsersDto.getUserId());
        assertEquals(usersDto4.getName(), actualUsersDto.getName());
        assertEquals(usersDto4.getPasswordHash(), actualUsersDto.getPasswordHash());
    }
}