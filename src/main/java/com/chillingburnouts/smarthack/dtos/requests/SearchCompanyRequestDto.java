package com.chillingburnouts.smarthack.dtos.requests;

import com.chillingburnouts.smarthack.dtos.FilterDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompanyRequestDto {
    private String companyName;
}
