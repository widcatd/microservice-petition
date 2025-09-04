package com.petition.model.state.gateways;

import com.petition.model.webclient.User;
import reactor.core.publisher.Mono;

public interface AuthClient {
    Mono<User> findByDocument(String identityDocument,String authHeader);
}
