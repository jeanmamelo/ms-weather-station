package com.portoseg.service.client;

import com.portoseg.configuration.ApplicationConfig;
import com.portoseg.constants.Constants;
import com.portoseg.model.dto.SpotifyCategoryByIdResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyClient {

    private final ApplicationConfig applicationConfig;
    private final RestTemplateBuilder restTemplateBuilder;

    /**
     * Method responsible for getting all playlists from a category on spotify
     *
     */
    public SpotifyCategoryByIdResponse getPlaylistsByCategoryId(String categoryId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(applicationConfig.getSpotifyCredential()));
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

        String spotifyEndpointsPlaylistsByCategory = String.format(applicationConfig.getSpotifyEndpointsPlaylistsByCategory(), categoryId);

        return restTemplateBuilder.build().exchange(spotifyEndpointsPlaylistsByCategory, HttpMethod.GET,
                entity, SpotifyCategoryByIdResponse.class).getBody();
    }

}
