package com.chillingburnouts.smarthack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chillingburnouts.smarthack.entities.Course;
import com.chillingburnouts.smarthack.entities.User;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
