package com.chillingburnouts.smarthack.controllers;

import com.chillingburnouts.smarthack.apis.VeridionClient;
import com.chillingburnouts.smarthack.dtos.CompanyDto;
import com.chillingburnouts.smarthack.dtos.FilterDto;
import com.chillingburnouts.smarthack.dtos.requests.SearchCompanyRequestDto;
import com.chillingburnouts.smarthack.dtos.requests.SearchCompanyRequestVeridion;
import com.chillingburnouts.smarthack.dtos.responses.requests.SearchCompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final VeridionClient veridionClient;

    @PostMapping()
    public ResponseEntity<CompanyDto> getCompany(@RequestBody CompanyDto body){
        var data =veridionClient.getData(body);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchCompanyResponse> searchCompany(@RequestBody SearchCompanyRequestDto body){

        var request = SearchCompanyRequestVeridion.builder()
                .filters(List.of(FilterDto.builder()
                                .attribute("company_name")
                                .value(body.getCompanyName())
                                .relation("matches")
                                .build())).build();
        var data =veridionClient.searchCompany(request);
        return ResponseEntity.ok(data);
    }
}
