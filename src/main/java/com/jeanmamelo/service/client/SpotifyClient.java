package com.jeanmamelo.service.client;

import com.jeanmamelo.configuration.ApplicationConfig;
import com.jeanmamelo.constants.Constants;
import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.SpotifyCategoryByIdResponse;
import com.jeanmamelo.model.dto.SpotifyPlaylistByIdResponse;
import com.jeanmamelo.model.dto.SpotifyTokenResponse;
import com.jeanmamelo.model.dto.SpotifyUserByIdResponse;
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
        log.info("Calling Spotify API with the following categoryId: {}", categoryId);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(getToken().getAccessToken()));
            httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

            String url = String.format(applicationConfig.getSpotifyEndpointsPlaylistsByCategory(), categoryId);

            return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                    entity, SpotifyCategoryByIdResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error on retrieving categoryId from Spotify API. Details: {} \n Error: {}", e.getMessage(), e);
            throw new UnprocessableEntityException("422.003");
        }
    }

    public SpotifyPlaylistByIdResponse getTracksByPlaylistId(String playlistId) {
        log.info("Calling Spotify API with the following playlistId: {}", playlistId);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(getToken().getAccessToken()));
            httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

            String url = String.format(applicationConfig.getSpotifyEndpointsPlaylistById(), playlistId);

            return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                    entity, SpotifyPlaylistByIdResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error on retrieving playlistId from Spotify API. Details: {} \n Error: {}", e.getMessage(), e);
            throw new UnprocessableEntityException("422.004");
        }
    }

    public SpotifyUserByIdResponse getUserInfoById(String spotifyId) {
        log.info("Calling Spotify API with the following spotify ID: {}", spotifyId);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BEARER.concat(getToken().getAccessToken()));
            httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

            String url = String.format(applicationConfig.getSpotifyEndpointsUserById(), spotifyId);

            return restTemplateBuilder.build().exchange(url, HttpMethod.GET,
                    entity, SpotifyUserByIdResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error on retrieving user information from Spotify API. Details: {} \n Error: {}", e.getMessage(), e);
            throw new UnprocessableEntityException("422.005");
        }
    }

    private SpotifyTokenResponse getToken() {
        log.info("Generating Spotify token...");
        try {
            String authorization = Base64.getEncoder()
                    .encodeToString((
                            applicationConfig.getSpotifyCredentialClientId() + ":" + applicationConfig.getSpotifyCredentialClientSecret())
                            .getBytes());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(Constants.SPOTIFY_AUTHORIZATION, Constants.SPOTIFY_BASIC.concat(authorization));
            httpHeaders.add(Constants.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            String body = "grant_type=client_credentials";

            HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);

            String url = applicationConfig.getSpotifyEndpointsGenerateToken();

            return restTemplateBuilder.build().exchange(url, HttpMethod.POST, entity, SpotifyTokenResponse.class).getBody();
        } catch (Exception e) {
            log.error("Error on generating Spotify token. Details: {} \n Error: {}", e.getMessage(), e);
            throw new UnprocessableEntityException("422.002");
        }
    }
}
