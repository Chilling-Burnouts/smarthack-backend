package com.chillingburnouts.smarthack.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chillingburnouts.smarthack.dtos.UserDto;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> mappedUsers = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mappedUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        UserDto userDto = convertToDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        User mappedUser = convertToEntity(userDto);
        User savedUser = userService.save(mappedUser);
        return ResponseEntity.ok(convertToDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User mappedUser = convertToEntity(userDto);
        User savedUser = userService.updateUser(id, mappedUser);
        UserDto userMappedDto = convertToDto(savedUser);
        return ResponseEntity.ok(userMappedDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User updatedUser = userService.patchUser(id, updates);
        UserDto mappedUser = convertToDto(updatedUser);
        return ResponseEntity.ok(mappedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if (userDto.getId() != null) {
            user = userService.findById(userDto.getId());
        }
        return user;
    }
}
