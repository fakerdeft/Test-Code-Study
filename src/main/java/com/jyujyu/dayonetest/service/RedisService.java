package com.jyujyu.dayonetest.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
}
