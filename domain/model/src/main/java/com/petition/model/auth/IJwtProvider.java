package com.petition.model.auth;

import reactor.core.publisher.Mono;

public interface IJwtProvider {
    Mono<String> getSubject();
}
