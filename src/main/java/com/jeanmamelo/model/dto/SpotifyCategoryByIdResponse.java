package com.jeanmamelo.model.dto;

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
public class SpotifyCategoryByIdResponse {

    private SpotifyPlaylistResponse playlists;

}
