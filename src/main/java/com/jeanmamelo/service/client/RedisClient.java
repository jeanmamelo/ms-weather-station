package com.jeanmamelo.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisClient {
//
//    private final StringRedisTemplate redis;
//    private final ApplicationConfig applicationConfig;
//    private final JsonConfig jsonConfig;
//
//    public void set(String key, Object value) {
//        redis.opsForValue().set(String.format(applicationConfig.getRedisPlaylistPrefix(), key),
//                        jsonConfig.objectToStringJson(value), 24, TimeUnit.HOURS);
//    }
//
//    public <T> Optional<T> get(String key, Class<T> clazz) {
//        return Optional.ofNullable(redis.opsForValue().get(String.format(applicationConfig.getRedisPlaylistPrefix(), key)))
//                .map(value -> jsonConfig.stringJsonToObject(value, clazz));
//    }
}
