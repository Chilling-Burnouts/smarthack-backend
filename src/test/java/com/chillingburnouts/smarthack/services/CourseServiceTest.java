package com.chillingburnouts.smarthack.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.CourseRepository;
import com.chillingburnouts.smarthack.services.CourseService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findAll() {
    }

    @Test
    void save() {
        // given
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // when
        Course result = courseService.save(course);

        // then
        assertEquals(course, result);
        verify(courseRepository).save(eq(course));
    }

    @Test
    void updateCourse() {
        // given
        Course existingCourse = new Course();
        Course updatedCourse = new Course();
        updatedCourse.setName("Updated Name");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        // when
        Course result = courseService.updateCourse(1L, updatedCourse);

        // then
        assertEquals(updatedCourse, result);
        verify(courseRepository).save(eq(existingCourse));
    }

    @Test
    void patchCourse() {
        // given
        Course existingCourse = new Course();
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Patched Name");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);

        // when
        Course result = courseService.patchCourse(1L, updates);

        // then
        assertEquals("Patched Name", result.getName());
        verify(courseRepository).save(eq(existingCourse));
    }

    @Test
    void deleteCourse() {
        // given
        Course existingCourse = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));

        // when
        courseService.deleteCourse(1L);

        // then
        verify(courseRepository).deleteById(eq(1L));
    }

    @Test
    void deleteCourse_shouldThrowExceptionWhenCourseNotFound() {
        // given
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(1L));
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        Course course = new Course();
        course.setId(id);
        when(courseRepository.findById(eq(id))).thenReturn(Optional.of(course));

        // Act
        Course result = courseService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(courseRepository, times(1)).findById(eq(id));
    }
}