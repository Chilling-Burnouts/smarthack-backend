package com.chillingburnouts.smarthack.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chillingburnouts.smarthack.dtos.CourseDto;
import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.services.CourseService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<CourseDto>> findAllCourses() {
        List<Course> courses = courseService.findAll();
        List<CourseDto> mappedCourses = courses.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(mappedCourses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findCourse(@PathVariable("id") Long id) {
        CourseDto courseDto = convertToDto(courseService.findById(id));
        return ResponseEntity.ok(courseDto);
    }

    @PostMapping()
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto course) {
        Course mappedCourse = convertToEntity(course);
        Course savedCourse = courseService.save(mappedCourse);
        return ResponseEntity.ok(convertToDto(savedCourse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course mappedCourse = convertToEntity(courseDto);
        Course updatedCourse = courseService.updateCourse(id, mappedCourse);
        return ResponseEntity.ok(convertToDto(updatedCourse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseDto> patchCourse(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Course updatedCourse = courseService.patchCourse(id, updates);
        CourseDto mappedCourse = convertToDto(updatedCourse);
        return ResponseEntity.ok(mappedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    private CourseDto convertToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    private Course convertToEntity(CourseDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        if (courseDto.getId() != null) {
            course = courseService.findById(courseDto.getId());
        }
        return course;
    }

}
