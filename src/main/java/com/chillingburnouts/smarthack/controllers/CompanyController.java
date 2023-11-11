package com.chillingburnouts.smarthack.controllers;

import com.chillingburnouts.smarthack.apis.VeridionClient;
import com.chillingburnouts.smarthack.dtos.CompanyDto;
import com.chillingburnouts.smarthack.dtos.FilterDto;
import com.chillingburnouts.smarthack.dtos.requests.search.SearchCompanyRequestDto;
import com.chillingburnouts.smarthack.dtos.requests.search.SearchCompanyRequestVeridion;
import com.chillingburnouts.smarthack.dtos.responses.requests.SearchCompanyResponse;
import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.entities.Portofolio;
import com.chillingburnouts.smarthack.entities.User;
import com.chillingburnouts.smarthack.repositories.CompanyRepository;
import com.chillingburnouts.smarthack.repositories.PortofolioRepository;
import com.chillingburnouts.smarthack.repositories.UserRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    private final VeridionClient veridionClient;

    private final UserRepository userRepository;

    private final PortofolioRepository portofolioRepository;
    private final CompanyRepository companyRepository;

    @PostMapping("/search")
    public ResponseEntity<SearchCompanyResponse> searchCompany(@RequestBody SearchCompanyRequestDto body){

        var request = SearchCompanyRequestVeridion.builder()
                .filters(List.of(FilterDto.builder()
                                .attribute("company_name")
                                .value(body.getCompanyName())
                                .relation("matches")
                                .build())).build();
        var data =veridionClient.searchCompany(request);
        data.getCompanies().forEach(c -> c.setUuid(UUID.randomUUID().toString()));
        return ResponseEntity.ok(data);
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<Void> saveCompany(@RequestBody CompanyDto body){
        Optional<User> userOptional = userRepository.findByName("user");
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create and save company
        Company company = new Company();
        company.setUuid(body.getUuid());
        company.setNaicsCode(body.getNaics().getPrimaryNaics().getCode());
        company.setNaicsLabel(body.getNaics().getPrimaryNaics().getLabel());
        company.setCompanyName(body.getCompanyName());
        company.setBusinessTags(body.getBusinessTags());
        company.setLongDescription(body.getLongDescription());
        company.setMainCountry(body.getMainCountry());
        company.setShortDescription(body.getShortDescription());
        Company savedCompany = companyRepository.save(company);

        // Check if user has a portfolio, if not create and set a new one
        Portofolio portfolio = user.getPortofolio();
        if (portfolio == null) {
            portfolio = new Portofolio();
            user.setPortofolio(portfolio);
        }

        // Initialize the companies set if it's null
        if (portfolio.getCompanies() == null) {
            portfolio.setCompanies(new HashSet<>());
        }

        // Add the saved company to the user's portfolio
        portfolio.getCompanies().add(savedCompany);

        // Save or update the portfolio and user
        portofolioRepository.save(portfolio);
        userRepository.save(user);

        return ResponseEntity.status(201).build();
    }
}
