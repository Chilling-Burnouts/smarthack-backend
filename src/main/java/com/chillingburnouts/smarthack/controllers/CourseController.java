package com.chillingburnouts.smarthack.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chillingburnouts.smarthack.dtos.CourseDto;
import com.chillingburnouts.smarthack.entities.Company;
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
        List<Company> cours = courseService.findAll();
        List<CourseDto> mappedCourses = cours.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(mappedCourses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> findCourse(@PathVariable("id") Long id) {
        CourseDto courseDto = convertToDto(courseService.findById(id));
        return ResponseEntity.ok(courseDto);
    }

    @PostMapping()
    public ResponseEntity<CourseDto> saveCourse(@RequestBody CourseDto course) {
        Company mappedCompany = convertToEntity(course);
        Company savedCompany = courseService.save(mappedCompany);
        return ResponseEntity.ok(convertToDto(savedCompany));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Company mappedCompany = convertToEntity(courseDto);
        Company updatedCompany = courseService.updateCourse(id, mappedCompany);
        return ResponseEntity.ok(convertToDto(updatedCompany));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseDto> patchCourse(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Company updatedCompany = courseService.patchCourse(id, updates);
        CourseDto mappedCourse = convertToDto(updatedCompany);
        return ResponseEntity.ok(mappedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    private CourseDto convertToDto(Company company) {
        return modelMapper.map(company, CourseDto.class);
    }

    private Company convertToEntity(CourseDto courseDto) {
        Company company = modelMapper.map(courseDto, Company.class);
        if (courseDto.getId() != null) {
            company = courseService.findById(courseDto.getId());
        }
        return company;
    }

}
