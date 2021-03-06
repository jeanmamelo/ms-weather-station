package com.jeanmamelo.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationConfig {

    @Value("${spotify.credentials.client-id}")
    private String spotifyCredentialClientId;

    @Value("${spotify.credentials.client-secret}")
    private String spotifyCredentialClientSecret;

    @Value("${spotify.endpoints.playlists-by-category}")
    private String spotifyEndpointsPlaylistsByCategory;

    @Value("${spotify.endpoints.playlist-by-id}")
    private String spotifyEndpointsPlaylistById;

    @Value("${spotify.endpoints.generate-token}")
    private String spotifyEndpointsGenerateToken;

    @Value("${spotify.endpoints.user-by-id}")
    private String spotifyEndpointsUserById;

    @Value("${open-weather.credential}")
    private String openWeatherCredential;

    @Value("${open-weather.endpoints.location-by-city-name}")
    private String openWeatherEndpointsLocationByCityName;

    @Value("${open-weather.endpoints.location-by-coordinates}")
    private String openWeatherEndpointsLocationByCoordinates;

    @Value("${spring.redis.key-prefix.playlist}")
    private String redisPlaylistPrefix;

}
