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
import com.chillingburnouts.smarthack.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final VeridionClient veridionClient;

    private final UserRepository userRepository;

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

    @PostMapping()
    public ResponseEntity<Void> saveCompany(@RequestBody CompanyDto body, Principal principal){
        Optional<User> u = userRepository.findByName(principal.getName());
        User up = u.orElseThrow(()->new IllegalArgumentException());
        Company company = new Company();
        company.setCompanyName(body.getCompanyName());
        company.setBusinessTags(body.getBusinessTags());
        company.setLongDescription(body.getLongDescription());
        company.setMainCountry(body.getMainCountry());
        company.setShortDescription(body.getShortDescription());
        if(up.getPortofolio() == null){
            up.setPortofolio(new Portofolio());
        }
        up.getPortofolio().getCompanies().add(company);
        userRepository.save(up);
        return ResponseEntity.status(201).build();
    }
}
