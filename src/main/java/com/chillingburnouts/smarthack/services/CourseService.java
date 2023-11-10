package com.chillingburnouts.smarthack.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.CourseRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course course) { // todo change
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingCourse.setName(course.getName());
        existingCourse.setUsers(course.getUsers());
        return courseRepository.save(existingCourse);
    }

    public Course patchCourse(Long id, Map<String, Object> updates) {
        // Find the existing course, apply the updates, and save it
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (updates.containsKey("name")) {
            existingCourse.setName((String) updates.get("name"));
        }
        if (updates.containsKey("users")) { // todo here is should be List<UserDto>
            // Assume that updates.get("courses") returns a List<Course>
            existingCourse.setUsers((Set<User>) updates.get("users"));
        }
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        courseRepository.deleteById(id);
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }
}
