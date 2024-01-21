package com.API.ratelimiter.SlidingWindowCounter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SlidingWindowCounter {

  @Autowired
  private RedisTemplate<String, Integer> redisTemplate;
  private final static int maxRequest = 10;
  private final static int windowSizeInMillis = 1000;
//  private final Map<String, int[]> userWindowCounts;
//
//
//  public SlidingWindowCounter() {
//    this.userWindowCounts = new HashMap<>();
////    this.lastRequestTime = System.currentTimeMillis();
//  }

  public synchronized boolean allowRequest(String userID) {
    long currentTime = System.currentTimeMillis();

    String key = "sliding_window_counter:" + userID;

    int currentWindowIndex = (int) (currentTime / windowSizeInMillis) %2;
    int previousWindowIndex = (currentWindowIndex + 1) % 2;

    float currentWeight = 0.4f;
    float previousWeight = 0.6f;

    int currentCount = Math.toIntExact(
        redisTemplate.opsForHash().increment(key, String.valueOf(currentWindowIndex), 0));
    int previousCount = Math.toIntExact(
        redisTemplate.opsForHash().increment(key, String.valueOf(previousWindowIndex), 0));
    int slidingWindowCount = (int) ((currentWeight * currentCount) + (previousWeight * previousCount));

    if(slidingWindowCount < maxRequest){

      redisTemplate.opsForHash().increment(key, String.valueOf(currentWindowIndex), 1 );
      return true;
    }

    return false;
  }
}
