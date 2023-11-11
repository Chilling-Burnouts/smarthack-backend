package com.chillingburnouts.smarthack.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.CompanyRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findAll() {
    }

    @Test
    void save() {
        // given
        Company company = new Company();
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // when
        Company result = courseService.save(company);

        // then
        assertEquals(company, result);
        verify(companyRepository).save(eq(company));
    }

    @Test
    void updateCourse() {
        // given
        Company existingCompany = new Company();
        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Name");
        when(companyRepository.findById(1L)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        // when
        Company result = courseService.updateCourse(1L, updatedCompany);

        // then
        assertEquals(updatedCompany, result);
        verify(companyRepository).save(eq(existingCompany));
    }

    @Test
    void patchCourse() {
        // given
        Company existingCompany = new Company();
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Patched Name");
        when(companyRepository.findById(1L)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(existingCompany);

        // when
        Company result = courseService.patchCourse(1L, updates);

        // then
        assertEquals("Patched Name", result.getName());
        verify(companyRepository).save(eq(existingCompany));
    }

    @Test
    void deleteCourse() {
        // given
        Company existingCompany = new Company();
        when(companyRepository.findById(1L)).thenReturn(Optional.of(existingCompany));

        // when
        courseService.deleteCourse(1L);

        // then
        verify(companyRepository).deleteById(eq(1L));
    }

    @Test
    void deleteCourse_shouldThrowExceptionWhenCourseNotFound() {
        // given
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(1L));
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        Company company = new Company();
        company.setId(id);
        when(companyRepository.findById(eq(id))).thenReturn(Optional.of(company));

        // Act
        Company result = courseService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(companyRepository, times(1)).findById(eq(id));
    }
}