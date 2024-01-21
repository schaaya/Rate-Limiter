package com.API.ratelimiter.TokenBucket.Service;

import ch.qos.logback.core.util.TimeUtil;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.stereotype.Service;


//Token-Bucket Algorithm

@Service
public class RateLimiterService {
  private Map<String,ArrayDeque<Integer>> map = new HashMap<>();
  private Map<String,Integer> requestCountMap = new HashMap<>();
  private int requestLimit =10;

  public RateLimiterService(){
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        addToken();
      }
    }, 0, 1000);
  }

  public synchronized Boolean controlUserBuckets(String userId){

    map.putIfAbsent(userId, new ArrayDeque<>(requestLimit));
    requestCountMap.putIfAbsent(userId,0);
    ArrayDeque<Integer> userBucket = map.get(userId);
    int requestCount = requestCountMap.get(userId);
    if( requestCount > requestLimit) return false;

    if(userBucket !=null){
      if(userBucket.isEmpty()) fillUserBucket(userBucket);

      userBucket.pop();
      requestCountMap.put(userId, requestCount +1 );
      return true;

    }else {
      return false;
    }
  }

  private void fillUserBucket(ArrayDeque<Integer> userBucket) {

    for(int i =0;i <requestLimit;i++){
      userBucket.push(1);
    }
  }

  private synchronized void addToken() {
    for(ArrayDeque<Integer> userBucket : map.values()){
      userBucket.push(1);
    }
  }
}
