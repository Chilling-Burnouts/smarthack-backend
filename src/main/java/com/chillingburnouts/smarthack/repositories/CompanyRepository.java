package com.chillingburnouts.smarthack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chillingburnouts.smarthack.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
