package com.jeanmamelo.service;

import com.jeanmamelo.exception.NotFoundException;
import com.jeanmamelo.exception.UnprocessableEntityException;
import com.jeanmamelo.model.dto.SpotifyUserByIdResponse;
import com.jeanmamelo.model.dto.UserPatchRequest;
import com.jeanmamelo.model.dto.UserPutRequest;
import com.jeanmamelo.model.dto.UserRequest;
import com.jeanmamelo.model.dto.UserResponse;
import com.jeanmamelo.model.entity.UserEntity;
import com.jeanmamelo.repository.UserRepository;
import com.jeanmamelo.service.client.SpotifyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        log.info("Creating new user... \n {}", userRequest);
        checkSpotifyIdInUserRepository(userRequest.getSpotifyId());
        SpotifyUserByIdResponse spotifyInfo = spotifyClient.getUserInfoById(userRequest.getSpotifyId());

        String image = !spotifyInfo.getImages().isEmpty() ? spotifyInfo.getImages().get(0).getUrl() : "";
        UserEntity userEntity = UserEntity.valueOf(userRequest);
        userEntity.setFollowers(spotifyInfo.getFollowers().getTotal());
        userEntity.setDisplayName(spotifyInfo.getDisplayName());
        userEntity.setImage(image);

        UserEntity newUser = userRepository.save(userEntity);
        log.info("The user was saved successfully.");
        return newUser.getId();
    }

    public List<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Getting all users...");
        return userRepository.findAll(pageable)
                .stream()
                .map(UserResponse::valueOf)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id) {
        log.info("Getting user based on ID: {}", id);
        return userRepository.findById(id)
                .map(UserResponse::valueOf)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteUserById(Integer id) {
        log.info("Deleting user based on ID: {}", id);
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(Integer id, UserPutRequest userPutRequest) {
        log.info("Updating user based on ID: {}", id);
        userRepository.findById(id).orElseThrow(NotFoundException::new);
        checkSpotifyIdInUserRepository(userPutRequest.getSpotifyId());
        spotifyClient.getUserInfoById(userPutRequest.getSpotifyId());

        UserEntity userEntity = UserEntity.valueOf(userPutRequest);
        userEntity.setId(id);

        userRepository.save(userEntity);
        log.info("User ID {} updated successfully.", id);
    }

    public void partialUpdateUserById(Integer id, UserPatchRequest userPatchRequest) {
        log.info("Partially updating user based on ID: {}", id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(NotFoundException::new);

        if(!StringUtils.isEmpty(userPatchRequest.getSpotifyId())){
            checkSpotifyIdInUserRepository(userPatchRequest.getSpotifyId());
            spotifyClient.getUserInfoById(userPatchRequest.getSpotifyId());
            userEntity.setSpotifyId(userPatchRequest.getSpotifyId());
        }
        if(!StringUtils.isEmpty(userPatchRequest.getFullName())){
            userEntity.setFullName(userPatchRequest.getFullName());
        }
        if(userPatchRequest.getAge() != null){
            userEntity.setAge(userPatchRequest.getAge());
        }
        if(!StringUtils.isEmpty(userPatchRequest.getDisplayName())){
            userEntity.setDisplayName(userPatchRequest.getDisplayName());
        }
        if(userPatchRequest.getFollowers() != null){
            userEntity.setFollowers(userPatchRequest.getFollowers());
        }
        if(!StringUtils.isEmpty(userPatchRequest.getImage())){
            userEntity.setImage(userPatchRequest.getImage());
        }
        userRepository.save(userEntity);
        log.info("User with the ID {} has been updated successfully.", id);
    }

    private void checkSpotifyIdInUserRepository(String spotifyId) {
        log.info("Checking if spotify ID {} is in use in the database.", spotifyId);
        userRepository.findBySpotifyId(spotifyId).ifPresent(user -> {
            log.error("The following spotifyId already exists: {}", user.getSpotifyId());
            throw new UnprocessableEntityException("422.000");
        });
    }
}
