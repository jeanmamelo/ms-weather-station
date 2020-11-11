package com.portoseg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {

    private List<ArtistResponse> artists;
    private String name;
    private Integer popularity;

    public static PlaylistResponse valueOf(SpotifyTrackItemsResponse response) {

        List<ArtistResponse> artists = response.getTrack().getArtists()
            .stream()
            .map(artist -> {
                return ArtistResponse.builder().name(artist.getName()).build();
            })
            .collect(Collectors.toList());

        return PlaylistResponse.builder()
                .artists(artists)
                .name(response.getTrack().getName())
                .popularity(response.getTrack().getPopularity())
                .build();
    }
}
