package com.jeanmamelo.controller;

import com.jeanmamelo.model.dto.UserRequest;
import com.jeanmamelo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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
}
