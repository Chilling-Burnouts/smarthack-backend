package com.chillingburnouts.smarthack.dtos.keywords;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDto {
    String operator;
    List<Object> operands;
}
