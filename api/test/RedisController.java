package com.lessonlink.api.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/save")
    public String saveData(@RequestBody RedisRequestDto request) {
        redisTemplate.opsForValue().set(request.getKey(), request.getValue());
        return "Data saved!";
    }

    @GetMapping("/get")
    public String getData(@RequestBody RedisGetRequestDto request) {
        return (String) redisTemplate.opsForValue().get(request.getKey());
    }

    @Data
    static class RedisRequestDto {
        private String key;
        private String value;
    }

    @Data
    static class RedisGetRequestDto {
        private String key;
    }
}
