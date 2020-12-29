package com.jeanmamelo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenWeatherResponse {

    private OpenWeatherMainResponse main;

    @JsonProperty("name")
    private String locationName;

}
