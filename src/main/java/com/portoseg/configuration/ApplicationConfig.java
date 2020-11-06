package com.portoseg.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationConfig {

    @Value("${spotify.credentials.bearer}")
    private String spotifyCredential;

    @Value("${spotify.endpoints.playlists-by-category}")
    private String spotifyEndpointsPlaylistsByCategory;

    @Value("${spotify.endpoints.playlist-by-id}")
    private String spotifyEndpointsPlaylistById;

}
