package com.chillingburnouts.smarthack.dtos.keywords;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keywords {
    MatchDto match;
    MatchDto exclude;
}
