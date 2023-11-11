package com.chillingburnouts.smarthack.controllers;

import com.chillingburnouts.smarthack.dtos.*;
import com.chillingburnouts.smarthack.entities.Portofolio;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user")
    public ResponseEntity<UserDto> get(){
        User u = userRepository.findByName("user").orElseThrow(()-> new RuntimeException());
        if(u.getPortofolio() == null){
            var ud = UserDto.builder()
                    .name(u.getName())
                    .build();
            return ResponseEntity.ok(ud);
        }
        var ud = UserDto.builder()
                .name(u.getName())
                .portofolio(PortofolioDto.builder()
                        .companies(u.getPortofolio().getCompanies()
                                .stream().map(c -> CompanyDto.builder()
                                        .uuid(c.getUuid())
                                        .naics(Naics.builder()
                                                .primaryNaics(PrimaryNaics.builder()
                                                        .code(c.getNaicsCode())
                                                        .label(c.getUuid())
                                                        .build())
                                                .build())
                                        .companyName(c.getCompanyName())
                                        .businessTags(c.getBusinessTags())
                                        .mainCountry(c.getMainCountry())
                                        .longDescription(c.getLongDescription())
                                        .shortDescription(c.getShortDescription())
                                        .build()).collect(Collectors.toList()))
                        .build())
                .build();
        return ResponseEntity.ok(ud);
    }

    @DeleteMapping
    public void delete(){
        User u = userRepository.findByName("user").orElseThrow(()-> new RuntimeException());
        userRepository.delete(u);
    }

}


