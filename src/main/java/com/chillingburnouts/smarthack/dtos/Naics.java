package com.chillingburnouts.smarthack.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Naics {
    @JsonProperty("primary")
    private PrimaryNaics primaryNaics;
}
