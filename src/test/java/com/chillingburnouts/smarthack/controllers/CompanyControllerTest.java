package com.chillingburnouts.smarthack.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.chillingburnouts.smarthack.dtos.CourseDto;
import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.services.CourseService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseController courseController;

    @Test
    void findAllCourses() {
        // Arrange
        Company company = new Company();
        CourseDto courseDto = new CourseDto();
        when(courseService.findAll()).thenReturn(Collections.singletonList(company));
        when(modelMapper.map(company, CourseDto.class)).thenReturn(courseDto);

        // Act
        ResponseEntity<List<CourseDto>> response = courseController.findAllCourses();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(courseDto, response.getBody().get(0));
    }

    @Test
    void findCourse() {
        // Arrange
        Company company = new Company();
        CourseDto courseDto = new CourseDto();
        when(courseService.findById(anyLong())).thenReturn(company);
        when(modelMapper.map(company, CourseDto.class)).thenReturn(courseDto);

        // Act
        ResponseEntity<CourseDto> response = courseController.findCourse(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
    }

    @Test
    void saveCourse() {
        // Arrange
        CourseDto courseDto = new CourseDto();
        Company company = new Company();
        when(modelMapper.map(courseDto, Company.class)).thenReturn(company);
        when(courseService.save(company)).thenReturn(company);
        when(modelMapper.map(company, CourseDto.class)).thenReturn(courseDto);

        // Act
        ResponseEntity<CourseDto> response = courseController.saveCourse(courseDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
    }

    @Test
    void updateCourse() {
        // Arrange
        Long courseId = 1L;
        CourseDto courseDto = new CourseDto();
        Company company = new Company();
        when(modelMapper.map(courseDto, Company.class)).thenReturn(company);
        when(courseService.updateCourse(courseId, company)).thenReturn(company);
        when(modelMapper.map(company, CourseDto.class)).thenReturn(courseDto);

        // Act
        ResponseEntity<CourseDto> response = courseController.updateCourse(courseId, courseDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseDto, response.getBody());
    }

    @Test
    void patchCourse() {
        // Arrange
        Long courseId = 1L;
        Map<String, Object> updates = Map.of("name", "New Course Name");
        Company company = new Company();
        when(courseService.patchCourse(courseId, updates)).thenReturn(company);
        when(modelMapper.map(company, CourseDto.class)).thenReturn(new CourseDto());

        // Act
        ResponseEntity<CourseDto> response = courseController.patchCourse(courseId, updates);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteCourse() {
        // Arrange
        Long courseId = 1L;
        doNothing().when(courseService).deleteCourse(courseId);

        // Act
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(courseService, times(1)).deleteCourse(courseId);
    }
}