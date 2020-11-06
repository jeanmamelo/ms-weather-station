package com.portoseg.controller;

import com.portoseg.model.dto.PlaylistResponse;
import com.portoseg.model.dto.SpotifyCategoryByIdResponse;
import com.portoseg.service.PlaylistService;
import com.portoseg.service.client.SpotifyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SpotifyClient spotifyClient;

    @GetMapping
    public ResponseEntity<SpotifyCategoryByIdResponse> getPlaylists(
            @RequestParam(name = "location") String location) {

        return ResponseEntity.ok(spotifyClient.getPlaylistsByCategoryId("party"));
    }
}
