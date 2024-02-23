package com.praneeth.lab.config.security.custom;

import com.praneeth.lab.exception.dto.CustomServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static com.praneeth.lab.constants.AppConstants.DetailConstants.INTERNAL_CLIENT_BASIC_KEY;

/**
 * @author Navishka Darshana - navishka@jevigsoft.com
 * @project backend
 * @CreatedBy IntelliJ IDEA
 * @created 03/10/2023 - 11.28
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class AuthAPIHandler {


    @Value("${token.uri}")
    private String tokenUri;

    private final RestTemplate restTemplate;

    public JsonNode getAuthResponse(String username, String password, String clientId) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            httpHeaders.set("Authorization", "Basic " + INTERNAL_CLIENT_BASIC_KEY);


            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "password");
            requestBody.add("username", username);
            requestBody.add("password", password);


            log.info("Login username : " + username);
            log.info("Login password : " + password);
            log.info("client id : " + clientId);
            log.info("tokenUrl : " + tokenUri);
            log.info("Authorization : " + "Basic " + INTERNAL_CLIENT_BASIC_KEY);


            ResponseEntity<JsonNode> authResponseResponseEntity = restTemplate.postForEntity(tokenUri, new HttpEntity<>(requestBody, httpHeaders), JsonNode.class);
            log.info("programmatic login :: authResponseResponseEntity => {}", authResponseResponseEntity);
            return authResponseResponseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("Error auth req: {}" + e.getMessage() + "\t" + e.getStatusCode());
            e.printStackTrace();
            switch (e.getStatusCode()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    throw new AccessDeniedException("User login failed. Unauthorized credentials.");
            }
            throw new CustomServiceException(401, "Access denied!");
        }
    }

}
