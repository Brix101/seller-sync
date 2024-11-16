package com.brix.Seller_Sync.amazon.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.brix.Seller_Sync.amazon.exception.LWAException;
import com.brix.Seller_Sync.amazon.payload.LWARequest;
import com.brix.Seller_Sync.amazon.payload.LWAResponse;
import com.brix.Seller_Sync.amazon.service.AmznLWAService;
import com.brix.Seller_Sync.client.Client;

import lombok.extern.java.Log;

@Service
@Log
public class AmznLWAServiceImpl implements AmznLWAService {

    @Override
    public LWAResponse getAccessToken(Client client) throws LWAException {
        String url = "https://api.amazon.com/auth/o2/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<LWARequest> request = new HttpEntity<>(client.toAuthRequest(), headers);

        try {
            log.info("Refreshing: " + client.getClientId());

            ResponseEntity<LWAResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, LWAResponse.class);
            
            return response.getBody();
            
        }  catch (HttpClientErrorException.BadRequest e) {
            LWAResponse responseBody = e.getResponseBodyAs(LWAResponse.class);
            
            throw new LWAException(responseBody.getError(), responseBody.getErrorDescription(), "Error getting LWA Token");
        } catch (Exception e) {
            throw new RuntimeException("Error getting LWA Token", e);
        }

    }
}