package com.andrei.mobiletracker.security.microservicesprovider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class MicroserviceResponseProvider {

    private WebClient.Builder webClientBuilder;

    public MicroserviceResponseProvider(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Resource
    private HttpServletRequest httpServletRequest;

    public <T> T get(String path, Class<T> responseClass) {

        return sendRequestAndReceiveResponse(path, responseClass, buildWebClientForGetAndDelete().get());
    }

    public <T> T delete (String path, Class<T> responseClass) {

        return sendRequestAndReceiveResponse(path, responseClass, buildWebClientForGetAndDelete().delete());
    }

    public <T, S> T sendRequestWithBody (String path, HttpMethod method, Class<T> responseClass, S body) {

        switch (method) {
            case POST:
                return sendRequestAndReceiveResponse(path, responseClass, body, buildWebClientForPostAndPut().post());
            case PUT:
                return sendRequestAndReceiveResponse(path, responseClass, body, buildWebClientForPostAndPut().put());
            default:
                throw new IllegalArgumentException(method + "are not allowed here!");
        }
    }

    private <T> T sendRequestAndReceiveResponse(String path, Class<T> responseClass, WebClient.RequestHeadersUriSpec requestHeadersUriSpec) {

        return requestHeadersUriSpec
                .uri("http://" + path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }

    private <T, S> T sendRequestAndReceiveResponse(String path, Class<T> responseClass, S body, WebClient.RequestBodyUriSpec requestBodyUriSpec) {

        return requestBodyUriSpec
                .uri("http://" + path)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(responseClass)
                .block();
    }

    private WebClient buildWebClientForGetAndDelete() {

        String authorizationHeaderValue = getAuthorizationHeaderValue();
        if (authorizationHeaderValue == null)
            return webClientBuilder
                    .build();

        return webClientBuilder
                .defaultHeader(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue())
                .build();
    }

    private WebClient buildWebClientForPostAndPut() {

        String authorizationHeaderValue = getAuthorizationHeaderValue();
        if (authorizationHeaderValue == null)
            return webClientBuilder
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();

        return webClientBuilder
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorizationHeaderValue)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    protected String getAuthorizationHeaderValue() {

        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }
}

