package com.portoseg.service.client;

import com.portoseg.configuration.ApplicationConfig;
import com.portoseg.constants.Constants;
import com.portoseg.model.dto.SpotifyCategoryByIdResponse;
import com.portoseg.model.dto.SpotifyPlaylistByIdResponse;
import com.portoseg.model.dto.SpotifyTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyClient {

    private final ApplicationConfig applicationConfig;
    private final RestTemplateBuilder restTemplateBuilder;

    /**
     * Method responsible for getting all playlists from a category on spotify
     * @param categoryId id from a spotify category
     * @return {@SpotifyCategoryByIdResponse}
     */
    public SpotifyCategoryByIdResponse getPlaylistsByCategoryId(String categoryId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(getToken()));
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

        String url = String.format(applicationConfig.getSpotifyEndpointsPlaylistsByCategory(), categoryId);

        return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                entity, SpotifyCategoryByIdResponse.class).getBody();
    }

    public SpotifyPlaylistByIdResponse getTracksByPlaylistId(String playlistId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(getToken()));
        httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

        String url = String.format(applicationConfig.getSpotifyEndpointsPlaylistById(), playlistId);

        return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                entity, SpotifyPlaylistByIdResponse.class).getBody();
    }

    public String getToken() {
        String authorization = Base64.getEncoder()
                .encodeToString((
                        applicationConfig.getSpotifyCredentialClientId() + ":" + applicationConfig.getSpotifyCredentialClientSecret())
                        .getBytes());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BASIC.concat(authorization));
        httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        String body = "grant_type=client_credentials";

        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);

        String url = String.format(applicationConfig.getSpotifyEndpointsGenerateToken());

        SpotifyTokenResponse tokenResponse = restTemplateBuilder.build().exchange(url, HttpMethod.POST,
                entity, SpotifyTokenResponse.class).getBody();

        return tokenResponse.getAccessToken();
    }

}
