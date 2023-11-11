package com.chillingburnouts.smarthack.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.chillingburnouts.smarthack.dtos.UserDto;
import com.chillingburnouts.smarthack.entities.Portofolio;
import com.chillingburnouts.smarthack.services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PortofolioControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserController userController;

    @Test
    void findAllUsers() {
        // Arrange
        Portofolio portofolio = new Portofolio();
        UserDto userDto = new UserDto();
        when(userService.findAll()).thenReturn(Collections.singletonList(portofolio));
        when(modelMapper.map(portofolio, UserDto.class)).thenReturn(userDto);

        // Act
        ResponseEntity<List<UserDto>> response = userController.findAllUsers();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(userDto, response.getBody().get(0));
    }

    @Test
    void findUser() {
        // Arrange
        Portofolio portofolio = new Portofolio();
        UserDto userDto = new UserDto();
        when(userService.findById(anyLong())).thenReturn(portofolio);
        when(modelMapper.map(portofolio, UserDto.class)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.findUser(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void saveUser() {
        // Arrange
        UserDto userDto = new UserDto();
        Portofolio portofolio = new Portofolio();
        when(modelMapper.map(userDto, Portofolio.class)).thenReturn(portofolio);
        when(userService.save(portofolio)).thenReturn(portofolio);
        when(modelMapper.map(portofolio, UserDto.class)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.saveUser(userDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void updateUser() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto();
        Portofolio portofolio = new Portofolio();
        when(modelMapper.map(userDto, Portofolio.class)).thenReturn(portofolio);
        when(userService.updateUser(userId, portofolio)).thenReturn(portofolio);
        when(modelMapper.map(portofolio, UserDto.class)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userId, userDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void patchUser() {
        // Arrange
        Long userId = 1L;
        Map<String, Object> updates = Map.of("name", "New User Name");
        Portofolio portofolio = new Portofolio();
        when(userService.patchUser(userId, updates)).thenReturn(portofolio);
        when(modelMapper.map(portofolio, UserDto.class)).thenReturn(new UserDto());

        // Act
        ResponseEntity<UserDto> response = userController.patchUser(userId, updates);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteUser() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
    }
}