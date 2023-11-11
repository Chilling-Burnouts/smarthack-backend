package com.chillingburnouts.smarthack.dtos.requests;

import com.chillingburnouts.smarthack.dtos.FilterDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchCompanyRequestVeridion {
    @JsonProperty("filters")
    private List<FilterDto> filters;
}
