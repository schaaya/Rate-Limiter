  package com.API.ratelimiter.controller;



  import com.API.ratelimiter.FixedWindowCounter.FixedWindowCounterService;
  import com.API.ratelimiter.SlidingWindowCounter.SlidingWindowCounter;
  import com.API.ratelimiter.SlidingWindowRateLimiter.SlidingWindowService;
  import com.API.ratelimiter.TokenBucket.Service.RateLimiterService;
  import jakarta.servlet.http.HttpServletRequest;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;

  @RestController
  public class RateLimiterController {

    @Autowired
    private RateLimiterService rateLimiterService;


    @Autowired
    private FixedWindowCounterService fixedWindowCounterService;

    @Autowired
    private SlidingWindowService slidingWindowService;

    @Autowired
    private SlidingWindowCounter slidingWindowCounter;


    //  @GetMapping(path = "/unlimited")
  //  public ResponseEntity<String> getUnlimitedRate(){
  //    return new ResponseEntity<String>("Unlimited! Let's go!", HttpStatus.OK);
  //  }

    //token-based endpoint
    @GetMapping(path = "/limited")
    public ResponseEntity<String> getLimitedRate(HttpServletRequest request){
      String clientID = request.getRemoteAddr();
      try {
        if (rateLimiterService.controlUserBuckets(clientID)) {
          return new ResponseEntity<String>("Welocme to the page!", HttpStatus.OK);
        } else {
          throw new RuntimeException();
        }
      }catch (RuntimeException e){
        return new ResponseEntity<String>("Rate Limit exceeded. Please try again later", HttpStatus.TOO_MANY_REQUESTS);
      }
    }

    //fixed window counter endpoint
    @GetMapping(path = "/fixed-window/limited")
      public ResponseEntity<String> getFixedWindowRateLimiter(HttpServletRequest request){
        String clientID = request.getRemoteAddr();

        try{
          if(fixedWindowCounterService.processRequest(clientID)) {
            return new ResponseEntity<String>("Welcome to fixed window counter service", HttpStatus.OK);
          }else{
            throw new RuntimeException();
          }
      }catch(RuntimeException e){
          return new ResponseEntity<String>("Rate Limit exceeded. Please try again later", HttpStatus.TOO_MANY_REQUESTS);
      }
    }

    @GetMapping(path= "/sliding-window/limited")
    public ResponseEntity<String> getSlidingWindowRateLimiter(HttpServletRequest request){
      String clientID = request.getRemoteAddr();

      try{
        if(slidingWindowService.getLimit(clientID))
          return new ResponseEntity<>("Welcome to Sliding Window log service", HttpStatus.OK);
        else
          throw new RuntimeException();
      }catch (RuntimeException e){
        return new ResponseEntity<String>("Rate Limite Exceeded.Please try again later", HttpStatus.TOO_MANY_REQUESTS);
      }
    }

    @GetMapping(path= "/sliding-window-counter/limited")
    public ResponseEntity<String> getSlidingWindowRateLimit(HttpServletRequest request){
      String clientID = request.getRemoteAddr();

      try {
        if (slidingWindowCounter.allowRequest(clientID)) {
          return new ResponseEntity<>("Welcome to Sliding window counter algorithm", HttpStatus.OK);
        } else {
          throw new RuntimeException();
        }
      }catch(RuntimeException e){
        return new ResponseEntity<String>("Rate Limit Exceeded. Please try again later", HttpStatus.TOO_MANY_REQUESTS);
      }
    }

  }
