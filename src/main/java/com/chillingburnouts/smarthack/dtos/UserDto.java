package com.chillingburnouts.smarthack.dtos;

import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Set<CourseDto> courses;
}
