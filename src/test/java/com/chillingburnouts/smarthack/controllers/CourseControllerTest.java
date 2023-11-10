package com.chillingburnouts.smarthack.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.chillingburnouts.smarthack.controllers.CourseController;
import com.chillingburnouts.smarthack.dtos.CourseDto;
import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.services.CourseService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseController courseController;

    @Test
    void findAllCourses() {
        // Arrange
        Course course = new Course();
        CourseDto courseDto = new CourseDto();
        when(courseService.findAll()).thenReturn(Collections.singletonList(course));
        when(modelMapper.map(course, CourseDto.class)).thenReturn(courseDto);

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
        Course course = new Course();
        CourseDto courseDto = new CourseDto();
        when(courseService.findById(anyLong())).thenReturn(course);
        when(modelMapper.map(course, CourseDto.class)).thenReturn(courseDto);

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
        Course course = new Course();
        when(modelMapper.map(courseDto, Course.class)).thenReturn(course);
        when(courseService.save(course)).thenReturn(course);
        when(modelMapper.map(course, CourseDto.class)).thenReturn(courseDto);

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
        Course course = new Course();
        when(modelMapper.map(courseDto, Course.class)).thenReturn(course);
        when(courseService.updateCourse(courseId, course)).thenReturn(course);
        when(modelMapper.map(course, CourseDto.class)).thenReturn(courseDto);

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
        Course course = new Course();
        when(courseService.patchCourse(courseId, updates)).thenReturn(course);
        when(modelMapper.map(course, CourseDto.class)).thenReturn(new CourseDto());

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