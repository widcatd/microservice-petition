package com.petition.api.webclient;

import com.petition.api.webclient.dto.UserDto;
import reactor.core.publisher.Mono;

public interface IAuthenticationClient {
    Mono<UserDto> findByDocument(String identityDocument);
}
