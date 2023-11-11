package com.chillingburnouts.smarthack.dtos.responses.requests;

import com.chillingburnouts.smarthack.dtos.CompanyDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompanyResponse {
    @JsonProperty("result")
    List<CompanyDto> companies;
}
