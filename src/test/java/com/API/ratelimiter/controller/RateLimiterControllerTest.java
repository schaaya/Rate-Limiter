package com.API.ratelimiter.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.API.ratelimiter.FixedWindowCounter.FixedWindowCounterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(classes = RateLimiterControllerTest.class)
class RateLimiterControllerTest {



//  ObjectMapper objectMapper = new ObjectMapper();

//  ObjectWriter objectWriter = new ObjectWriter();

  @Mock
  private FixedWindowCounterService fixedWindowCounterService;

  @Mock
  private HttpServletRequest request;
  @InjectMocks
  private RateLimiterController rateLimiterController;


  @Test
  void processRequest_shouldAllowRequestWhenBelowLimit() throws Exception {
    String clientID = "127.0.0.1";
    Mockito.when(request.getRemoteAddr()).thenReturn(clientID);
    Mockito.when(fixedWindowCounterService.processRequest(clientID)).thenReturn(true);

    ResponseEntity<String> response = rateLimiterController.getFixedWindowRateLimiter(request);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Welcome to fixed window counter service", response.getBody());
  }
}