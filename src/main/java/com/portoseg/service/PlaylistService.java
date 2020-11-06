package com.portoseg.service;

import com.portoseg.model.dto.ArtistResponse;
import com.portoseg.model.dto.PlaylistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistService {

    public List<PlaylistResponse> getPlaylists() {
        return null;
    }

}
