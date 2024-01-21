package com.API.ratelimiter.FixedWindowCounter;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
//
//@Service
//public class FixedWindowCounterService {
//  private static final int WINDOW_SIZE_SECONDS = 60;
//  private static final int THRESHOLD = 10;
//
//  private ConcurrentHashMap<String, ConcurrentHashMap<Long, Integer>> userWindowCounters = new ConcurrentHashMap<>();
//
//  public boolean processRequest(String userID) {
//    long currentTimestamp = Instant.now().getEpochSecond();
//    long currentWindow = currentTimestamp / WINDOW_SIZE_SECONDS;
//
//    // Get or create the window counter map for the specific user ID
//    ConcurrentHashMap<Long, Integer> windowCounter = userWindowCounters
//        .computeIfAbsent(userID, k -> new ConcurrentHashMap<>());
//
//    // Increment the counter for the current window using compute method
//    int currentWindowCount = windowCounter.compute(currentWindow, (key, value) -> (value == null) ? 1 : value + 1);
//
//    // Check if the counter exceeds the threshold
//    if (currentWindowCount > THRESHOLD) {
//      // Request exceeds threshold, discard it
//      System.out.println("Request for User ID " + userID + " discarded - Threshold exceeded");
//      return false;
//    }
//
//    // Request within the threshold, process it
//    System.out.println("Request for User ID " + userID + " processed successfully");
//    return true;
//  }
//}

@Service
public class FixedWindowCounterService {
  private static final int WINDOW_SIZE = 60;
  private static final int THRESHOLD = 10;

  ConcurrentHashMap<String, ConcurrentHashMap<Long, Integer>> userWindowCounters = new ConcurrentHashMap<>();

  public Boolean processRequest(String userID){
    long timestamp = Instant.now().getEpochSecond();

    long window = timestamp /WINDOW_SIZE;

    ConcurrentHashMap<Long, Integer> windowCounter =  userWindowCounters.computeIfAbsent(userID, k -> new ConcurrentHashMap<>());

    int currentWindowCount = windowCounter.compute(window, (key,value)-> (value==null)? 1: value+1    );

    if(currentWindowCount > THRESHOLD){
      return false;
    }

    return true;
  }
}