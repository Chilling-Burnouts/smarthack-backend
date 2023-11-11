package com.chillingburnouts.smarthack.controllers;

import com.chillingburnouts.smarthack.entities.Portofolio;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.repositories.UserRepository;
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
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/save")
    public User save(){
        User user = new User();
        user.setName("user");
        return userRepository.save(user);
    }
}
