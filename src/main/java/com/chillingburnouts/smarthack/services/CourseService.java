package com.chillingburnouts.smarthack.services;

import com.chillingburnouts.smarthack.entities.Portofolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.exceptions.ResourceNotFoundException;
import com.chillingburnouts.smarthack.repositories.CompanyRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCourse(Long id, Company company) { // todo change
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingCompany.setName(company.getName());
        existingCompany.setUsers(company.getUsers());
        return companyRepository.save(existingCompany);
    }

    public Company patchCourse(Long id, Map<String, Object> updates) {
        // Find the existing course, apply the updates, and save it
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (updates.containsKey("name")) {
            existingCompany.setName((String) updates.get("name"));
        }
        if (updates.containsKey("users")) { // todo here is should be List<UserDto>
            // Assume that updates.get("courses") returns a List<Course>
            existingCompany.setUsers((Set<Portofolio>) updates.get("users"));
        }
        return companyRepository.save(existingCompany);
    }

    public void deleteCourse(Long id) {
        companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        companyRepository.deleteById(id);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }
}
