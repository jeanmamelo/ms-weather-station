package com.jeanmamelo.model.dto;

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
public class SpotifyPlaylistByIdResponse {

    private List<SpotifyTrackItemsResponse> items;

}
