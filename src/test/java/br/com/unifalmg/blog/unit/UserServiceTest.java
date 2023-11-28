package br.com.unifalmg.blog.unit;

import br.com.unifalmg.blog.entity.User;
import br.com.unifalmg.blog.exception.UserNotFoundException;
import br.com.unifalmg.blog.repository.UserRepository;
import br.com.unifalmg.blog.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("#findById > When the id is null > Throw an exception")
    void findByIdWhenTheIdIsNullThrowAnException() {
        assertThrows(IllegalArgumentException.class, () ->
                service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When a user is found > Return the user")
    void findByIdWhenTheIdIsNotNullWhenAUserIsFoundReturnTheUser() {
        when(repository.findById(1)).thenReturn(Optional.of(User.builder()
                        .id(1)
                        .name("Fellipe")
                        .username("felliperey")
                .build()));
        User response = service.findById(1);
        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Fellipe", response.getName()),
                () -> assertEquals("felliperey", response.getUsername())
        );
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no user is found > Throw an exception")
    void findByIdWhenTheIdIsNotNullWhenNoUserIsFoundThrowAnException() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () ->
                service.findById(2));
    }

    @Test
    @DisplayName("getAllUsers > When there are no users > return an empty list")
    void getAllUsersWhenThereAreNoUsersReturnAnEmptyList() {
        List<User> users = new ArrayList<User>();
        when(repository.findAll()).thenReturn(users);
        assertTrue(service.getAllUsers().isEmpty());
    }

    @Test
    @DisplayName("getAllUsers > when the list is not empty > return the users")
    void getAllUsersWhenTheListIsNotEmptyReturnTheUsers() {
        when(repository.findAll()).thenReturn(List.of(
                User.builder()
                        .id(1)
                        .name("Leanne Graham")
                        .username("Bret")
                        .build(),
                User.builder()
                        .id(2)
                        .name("Ervin Howell")
                        .username("Antonette")
                        .build(),
                User.builder()
                        .id(3)
                        .name("Mrs. Dennis Schulist")
                        .username("Leopoldo_Corkery")
                        .build())
        );
        List<User> users = service.getAllUsers();
        assertAll(
                ()->assertEquals(users.get(0).getName(),"Leanne Graham"),
                ()->assertEquals(users.get(0).getUsername(),"Bret"),
                ()->assertEquals(users.get(1).getName(),"Ervin Howell"),
                ()->assertEquals(users.get(1).getUsername(),"Antonette"),
                ()->assertEquals(users.get(2).getName(),"Mrs. Dennis Schulist"),
                ()->assertEquals(users.get(2).getUsername(),"Leopoldo_Corkery")
        );
    }



}
