package com.chillingburnouts.smarthack.services;

import com.chillingburnouts.smarthack.dtos.FilterDto;
import com.chillingburnouts.smarthack.dtos.keywords.Keywords;
import com.chillingburnouts.smarthack.dtos.keywords.MatchDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SearchService {
    public FilterDto getDateBetween(Long start, Long end){
        return FilterDto.builder()
                .value(List.of(start, end))
                .attribute("company_year_founded")
                .relation("between")
                .build();
    }

    public FilterDto getWithIndustry(List<String> industries){
        return FilterDto.builder()
                .value(industries)
                .attribute("company_industry")
                .relation("in")
                .build();
    }

    public FilterDto getWithRevenue(Long revenue){
        return FilterDto.builder()
                .value(revenue)
                .attribute("company_estimated_revenue")
                .relation("greater_than")
                .build();
    }

    public FilterDto getWithNasicsCode(String code){
        return FilterDto.builder()
                .value("code")
                .attribute("company_naics_code")
                .relation("equals")
                .build();
    }

    public FilterDto getWithKeywordsAndExclude(List<String> keywords, List<String> exclusions){
        return FilterDto.builder()
                .attribute("company_keywords")
                .relation("match_expression")
                .value(Keywords.builder()
                        .match(MatchDto.builder()
                                .operator("and")
                                .operands(List.of(MatchDto.builder()
                                                .operator("or")
                                                .operands(Arrays.asList(keywords.toArray()))
                                        .build()))
                                .build())
                        .exclude(MatchDto.builder()
                                .operator("and")
                                .operands(List.of(MatchDto.builder()
                                        .operator("or")
                                        .operands(Arrays.asList(exclusions.toArray()))
                                        .build()))
                                .build())
                        .build())
                .strictness(1L)
                .build();
    }

}
