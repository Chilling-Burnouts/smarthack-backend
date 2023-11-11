package com.chillingburnouts.smarthack.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class CompanyDto {
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("naics_2022")
    private Naics naics;
    @JsonProperty("main_country_code")
    private String mainCountry;
    @JsonProperty("business_tags")
    private List<String> businessTags;
    @JsonProperty("long_description")
    private String longDescription;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("year_founded")
    private Long yearFounded;

}
