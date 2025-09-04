package com.petition.api.webclient.impl;


import com.petition.api.webclient.IAuthenticationClient;
import com.petition.api.webclient.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationClient implements IAuthenticationClient {
    private final WebClient webClient;
    public AuthenticationClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080/api/v1/usuarios").build();
    }

    @Override
    public Mono<UserDto> findByDocument(String identityDocument) {
        return webClient.get()
                .uri("/findByDocument/{identityDocument}", identityDocument)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
