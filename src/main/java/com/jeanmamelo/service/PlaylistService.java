package com.jeanmamelo.service;

import com.jeanmamelo.enums.CategoryEnum;
import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.OpenWeatherMainResponse;
import com.jeanmamelo.model.dto.OpenWeatherResponse;
import com.jeanmamelo.model.dto.PlaylistResponse;
import com.jeanmamelo.model.dto.SpotifyCategoryByIdResponse;
import com.jeanmamelo.model.dto.SpotifyItemsResponse;
import com.jeanmamelo.model.dto.SpotifyPlaylistByIdResponse;
import com.jeanmamelo.model.dto.TrackResponse;
import com.jeanmamelo.service.client.OpenWeatherClient;
import com.jeanmamelo.service.client.RedisClient;
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
    private final RedisClient redis;

    public TrackResponse getTracks(String cityName, String lat, String lon) {
        log.info("Getting tracks...");
        OpenWeatherResponse openWeatherResponse = openWeatherClient.getTemperatureByLocation(cityName, lat, lon);

        String category = getCategory(openWeatherResponse.getMain());

        SpotifyCategoryByIdResponse spotifyPlaylists = spotifyClient.getPlaylistsByCategoryId(category);

        SpotifyItemsResponse playlistId = spotifyPlaylists.getPlaylists().getItems()
                .stream()
                .findFirst()
                .orElseThrow(() -> new UnprocessableEntityException("422.001"));

        SpotifyPlaylistByIdResponse tracksByPlaylistId = spotifyClient.getTracksByPlaylistId(playlistId.getId());

        List<PlaylistResponse> tracks = tracksByPlaylistId.getItems().stream().map(PlaylistResponse::valueOf).collect(Collectors.toList());

        TrackResponse response = TrackResponse.builder().tracks(tracks).build();

        redis.set(openWeatherResponse.getLocationName(), response);

        return response;
    }

    private String getCategory(OpenWeatherMainResponse openWeatherMainResponse) {
        log.info("Getting category based on the temperature: {}", openWeatherMainResponse.getTemp());
        String category;

        if(openWeatherMainResponse.getTemp() >= 0 && openWeatherMainResponse.getTemp() <= 10) {
            category = CategoryEnum.CHILL.getDescription();
        } else if(openWeatherMainResponse.getTemp() > 10 && openWeatherMainResponse.getTemp() <= 20) {
            category = CategoryEnum.FOCUS.getDescription();
        } else if(openWeatherMainResponse.getTemp() > 20) {
            category = CategoryEnum.PARTY.getDescription();
        } else {
            category = CategoryEnum.MOOD.getDescription();
        }
        log.info("Got category with success: {}", category);
        return category;
    }
}
