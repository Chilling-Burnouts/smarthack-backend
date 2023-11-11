package com.chillingburnouts.smarthack.controllers;

import com.chillingburnouts.smarthack.entities.Portofolio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chillingburnouts.smarthack.dtos.UserDto;
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
        List<Portofolio> portofolios = userService.findAll();
        List<UserDto> mappedUsers = portofolios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mappedUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable("id") Long id) {
        Portofolio portofolio = userService.findById(id);
        UserDto userDto = convertToDto(portofolio);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        Portofolio mappedPortofolio = convertToEntity(userDto);
        Portofolio savedPortofolio = userService.save(mappedPortofolio);
        return ResponseEntity.ok(convertToDto(savedPortofolio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Portofolio mappedPortofolio = convertToEntity(userDto);
        Portofolio savedPortofolio = userService.updateUser(id, mappedPortofolio);
        UserDto userMappedDto = convertToDto(savedPortofolio);
        return ResponseEntity.ok(userMappedDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Portofolio updatedPortofolio = userService.patchUser(id, updates);
        UserDto mappedUser = convertToDto(updatedPortofolio);
        return ResponseEntity.ok(mappedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto convertToDto(Portofolio portofolio) {
        return modelMapper.map(portofolio, UserDto.class);
    }

    private Portofolio convertToEntity(UserDto userDto) {
        Portofolio portofolio = modelMapper.map(userDto, Portofolio.class);
        if (userDto.getId() != null) {
            portofolio = userService.findById(userDto.getId());
        }
        return portofolio;
    }
}
