package com.jeanmamelo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    private String name;
    private Integer popularity;
    private List<ArtistResponse> artists;

    public static PlaylistResponse valueOf(SpotifyTrackItemsResponse response) {

        List<ArtistResponse> artists = new ArrayList<>();

        response.getTrack().getArtists().forEach(artist -> artists.add(ArtistResponse.builder()
                .name(artist.getName())
                .build()));

        return PlaylistResponse.builder()
                .artists(artists)
                .name(response.getTrack().getName())
                .popularity(response.getTrack().getPopularity())
                .build();
    }
}
