package com.chillingburnouts.smarthack.dtos.responses.requests;

import com.chillingburnouts.smarthack.dtos.CompanyDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchCompanyResponse {
    @JsonProperty("result")
    List<CompanyDto> companies;
}
