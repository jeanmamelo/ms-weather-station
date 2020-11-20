package com.jeanmamelo.service;

import com.jeanmamelo.model.dto.SpotifyUserByIdResponse;
import com.jeanmamelo.model.dto.UserRequest;
import com.jeanmamelo.model.entity.UserEntity;
import com.jeanmamelo.repository.UserRepository;
import com.jeanmamelo.service.client.SpotifyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SpotifyClient spotifyClient;
    private final UserRepository userRepository;

    public Integer createUser(UserRequest userRequest) {

        SpotifyUserByIdResponse spotifyInfo = spotifyClient.getUserInfoById(userRequest.getSpotifyId());

        UserEntity userEntity = UserEntity.valueOf(userRequest);
        userEntity.setFollowers(spotifyInfo.getFollowers().getTotal());
        userEntity.setDisplayName(spotifyInfo.getDisplayName());
        userEntity.setImage(spotifyInfo.getImages().get(0).getUrl());

        UserEntity newUser = userRepository.save(userEntity);

        return newUser.getId();
    }

}
