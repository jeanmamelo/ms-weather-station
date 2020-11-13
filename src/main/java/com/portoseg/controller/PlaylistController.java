package com.portoseg.controller;

import com.portoseg.model.dto.TrackResponse;
import com.portoseg.service.PlaylistService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<TrackResponse> getPlaylists(
            @RequestParam(name = "cityName", required = false) String cityName,
            @RequestParam(name = "lat", required = false) String lat,
            @RequestParam(name = "lon", required = false) String lon) {

        return ResponseEntity.ok(playlistService.getTracks(cityName, lat, lon));
    }
}
