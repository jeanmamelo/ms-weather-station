package com.jeanmamelo.controller;

import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.TrackResponse;
import com.jeanmamelo.service.PlaylistService;
import com.jeanmamelo.service.client.RedisClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final RedisClient redis;

    @GetMapping
    public ResponseEntity<TrackResponse> getPlaylists(
            @RequestParam(name = "cityName", required = false) String cityName,
            @RequestParam(name = "lat", required = false) String lat,
            @RequestParam(name = "lon", required = false) String lon) {

        try {
            TrackResponse tracks = playlistService.getTracks(cityName, lat, lon);
            return ResponseEntity.status(HttpStatus.OK).header("Cache", "false").body(tracks);
        } catch(Exception e) {
            TrackResponse tracks = redis.get(cityName, TrackResponse.class)
                    .orElseThrow(() -> new UnprocessableEntityException("422.007"));
            return ResponseEntity.status(HttpStatus.OK).header("Cache", "true").body(tracks);
        }
    }
}
