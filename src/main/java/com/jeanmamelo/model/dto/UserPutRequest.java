package com.jeanmamelo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPutRequest {

    @NotEmpty(message = "400.002")
    private String spotifyId;

    @NotEmpty(message = "400.002")
    private String fullName;

    @NotNull(message = "400.002")
    private Integer age;

    @NotEmpty(message = "400.002")
    private String displayName;

    @NotNull(message = "400.002")
    private BigInteger followers;

    @NotEmpty(message = "400.002")
    private String image;
}
