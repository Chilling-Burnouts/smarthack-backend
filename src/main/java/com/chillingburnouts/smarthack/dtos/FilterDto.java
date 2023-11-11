package com.chillingburnouts.smarthack.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterDto {
    private String attribute;
    private String relation;
    private Object value;
    private Long strictness;
}
