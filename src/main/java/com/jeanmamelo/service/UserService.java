package com.jeanmamelo.service;

import com.jeanmamelo.exception.NotFoundException;
import com.jeanmamelo.model.dto.SpotifyUserByIdResponse;
import com.jeanmamelo.model.dto.UserRequest;
import com.jeanmamelo.model.dto.UserResponse;
import com.jeanmamelo.model.entity.UserEntity;
import com.jeanmamelo.repository.UserRepository;
import com.jeanmamelo.service.client.SpotifyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SpotifyClient spotifyClient;
    private final UserRepository userRepository;

    public Integer createUser(UserRequest userRequest) {
        SpotifyUserByIdResponse spotifyInfo = spotifyClient.getUserInfoById(userRequest.getSpotifyId());

        String image = !spotifyInfo.getImages().isEmpty() ? spotifyInfo.getImages().get(0).getUrl() : "";
        UserEntity userEntity = UserEntity.valueOf(userRequest);
        userEntity.setFollowers(spotifyInfo.getFollowers().getTotal());
        userEntity.setDisplayName(spotifyInfo.getDisplayName());
        userEntity.setImage(image);

        UserEntity newUser = userRepository.save(userEntity);
        return newUser.getId();
    }

    public List<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(UserResponse::valueOf)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserResponse::valueOf)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteUserById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            userRepository.deleteById(id);
        }
    }
}
