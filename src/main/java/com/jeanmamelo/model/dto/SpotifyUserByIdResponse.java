package com.jeanmamelo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpotifyUserByIdResponse {

    @JsonProperty("display_name")
    private String displayName;

    private SpotifyUserFollowersResponse followers;

    private List<SpotifyUserImageResponse> images;

}
