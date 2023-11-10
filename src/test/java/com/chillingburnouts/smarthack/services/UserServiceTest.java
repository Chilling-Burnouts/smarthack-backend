package com.chillingburnouts.smarthack.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.UserRepository;
import com.chillingburnouts.smarthack.services.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void findAll() {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();
        assertEquals(userList, result);
    }

    @Test
    void findById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);
        assertEquals(user, result);
    }

    @Test
    void save() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);
        assertEquals(user, result);
    }

    @Test
    void updateUser() {
        User existingUser = new User();
        User newUser = new User();
        newUser.setName("New name");
        newUser.setCourses(new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, newUser);
        assertEquals("New name", result.getName());
    }

    @Test
    void patchUser() {
        User existingUser = new User();
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated name");
        Set<Course> updatedCourses = new HashSet<>();
        updates.put("courses", updatedCourses);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.patchUser(1L, updates);
        assertEquals("Updated name", result.getName());
        assertEquals(updatedCourses, result.getCourses());
    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });
    }
}