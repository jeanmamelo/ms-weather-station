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
public class TrackResponse {

    private List<PlaylistResponse> tracks;

    public static TrackResponse valueOf(SpotifyPlaylistByIdResponse response) {
    }
}