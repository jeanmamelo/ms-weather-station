package com.jeanmamelo.model.entity;

import com.jeanmamelo.model.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "followers")
    private BigInteger followers;

    @Column(name = "image")
    private String image;

    public static UserEntity valueOf(UserRequest userRequest){
        return UserEntity.builder()
                .spotifyId(userRequest.getSpotifyId())
                .fullName(userRequest.getFullName())
                .age(userRequest.getAge())
                .build();
    }
}
