package com.portoseg.model.dto;

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
public class SpotifyTrackResponse {

    private List<SpotifyArtistsResponse> artists;
    private String name;
    private Integer popularity;

}
