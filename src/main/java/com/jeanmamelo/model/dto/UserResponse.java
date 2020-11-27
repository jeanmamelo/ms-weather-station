package com.jeanmamelo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jeanmamelo.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResponse {

    private String displayName;
    private Integer age;
    private BigInteger followers;
    private String imageUrl;

    public static UserResponse valueOf(UserEntity userEntity) {
        return UserResponse.builder()
                .displayName(userEntity.getDisplayName())
                .age(userEntity.getAge())
                .followers(userEntity.getFollowers())
                .imageUrl(userEntity.getImage())
                .build();
    }
}
