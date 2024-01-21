package com.API.ratelimiter.SlidingWindowRateLimiter;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.springframework.stereotype.Service;

@Service
public class SlidingWindowService {
//ConcurrentHashMap - request and Hashset {timestamp logs}
  // log with timestamps beyond a threshold are discarded.
  private final static int WINDOW_LIMIT = 60000;
  private final static int THRESHOLD = 10;


  ConcurrentHashMap<String, Deque<Long>> timestampLog = new ConcurrentHashMap<String, Deque<Long>>();

  public Boolean getLimit(String userId) {
    Deque<Long> clientTimestamps = timestampLog.computeIfAbsent(userId,
        k -> new ConcurrentLinkedDeque<>());

    long currentTimeMillis = System.currentTimeMillis();

    while (!clientTimestamps.isEmpty()
        && currentTimeMillis - clientTimestamps.peekFirst() > WINDOW_LIMIT) {
      clientTimestamps.pollFirst();
    }

    if (clientTimestamps.size() < THRESHOLD) {
      clientTimestamps.addLast(currentTimeMillis);
      return true;
    }

    return false;
  }

}
