package com.jeanmamelo.controller;

import com.jeanmamelo.model.dto.UserPatchRequest;
import com.jeanmamelo.model.dto.UserPutRequest;
import com.jeanmamelo.model.dto.UserRequest;
import com.jeanmamelo.model.dto.UserResponse;
import com.jeanmamelo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestBody @Valid UserRequest userRequest,
            UriComponentsBuilder uriComponentsBuilder){
        Integer userId = userService.createUser(userRequest);

        return ResponseEntity.created(uriComponentsBuilder.path("/users/{user-id}").buildAndExpand(userId).toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(name = "_limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(name = "_offset", required = false, defaultValue = "0") Integer offset) {

        Pageable pageable = PageRequest.of(offset, limit);

        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable(name = "userId") Integer userId) {

        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable(name = "userId") Integer userId) {

        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(
            @PathVariable(name = "userId") Integer userId,
            @RequestBody @Valid UserPutRequest userPutRequest) {

        userService.updateUserById(userId, userPutRequest);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> partialUpdateUserById(
            @PathVariable(name = "userId") Integer userId,
            @RequestBody @Valid UserPatchRequest userPatchRequest) {

        userService.partialUpdateUserById(userId, userPatchRequest);

        return ResponseEntity.noContent().build();
    }
}
