package com.jeanmamelo.service;

import com.jeanmamelo.enums.CategoryEnum;
import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.OpenWeatherResponse;
import com.jeanmamelo.model.dto.PlaylistResponse;
import com.jeanmamelo.model.dto.SpotifyCategoryByIdResponse;
import com.jeanmamelo.model.dto.SpotifyItemsResponse;
import com.jeanmamelo.model.dto.SpotifyPlaylistByIdResponse;
import com.jeanmamelo.model.dto.TrackResponse;
import com.jeanmamelo.service.client.OpenWeatherClient;
import com.jeanmamelo.service.client.SpotifyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final OpenWeatherClient openWeatherClient;
    private final SpotifyClient spotifyClient;

    public TrackResponse getTracks(String cityName, String lat, String lon) {

        OpenWeatherResponse temperatureByCityName = openWeatherClient.getTemperatureByLocation(cityName, lat, lon);

        String category = getCategory(temperatureByCityName);

        SpotifyCategoryByIdResponse spotifyPlaylists = spotifyClient.getPlaylistsByCategoryId(category);

        SpotifyItemsResponse playlistId = spotifyPlaylists.getPlaylists().getItems()
                .stream()
                .findFirst()
                .orElseThrow(() -> new UnprocessableEntityException("422.001"));

        SpotifyPlaylistByIdResponse tracksByPlaylistId = spotifyClient.getTracksByPlaylistId(playlistId.getId());

        List<PlaylistResponse> tracks = tracksByPlaylistId.getItems().stream().map(PlaylistResponse::valueOf).collect(Collectors.toList());

        return TrackResponse.builder().tracks(tracks).build();
    }

    private String getCategory(OpenWeatherResponse temperatureByCityName) {
        String response;

        if(temperatureByCityName.getMain().getTemp() >= 0 && temperatureByCityName.getMain().getTemp() <= 10) {
            response = CategoryEnum.CHILL.getDescription();
        } else if(temperatureByCityName.getMain().getTemp() > 10 && temperatureByCityName.getMain().getTemp() <= 20) {
            response = CategoryEnum.FOCUS.getDescription();
        } else if(temperatureByCityName.getMain().getTemp() > 20) {
            response = CategoryEnum.PARTY.getDescription();
        } else {
            response = CategoryEnum.MOOD.getDescription();
        }
        return response;
    }
}