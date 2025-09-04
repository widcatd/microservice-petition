package com.petition.jwt;

import com.petition.model.exception.JwtAuthenticationException;
import com.petition.model.exceptionusecase.ExceptionUseCaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;

@Component
public class JwtFilter implements WebFilter {
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/auth/**",
            "/swagger-ui.html",
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**"
    );
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = exchange.getRequest().getPath().value();
        if (PUBLIC_PATHS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path))) {
            return chain.filter(exchange);
        }
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(auth == null)
            return Mono.error(new JwtAuthenticationException(
                    ExceptionUseCaseResponse.JWT_TOKEN_NOT_FOUND.getCode(),
                    ExceptionUseCaseResponse.JWT_TOKEN_NOT_FOUND.getMessage()
            ));
        if(!auth.startsWith("Bearer "))
            return Mono.error(new JwtAuthenticationException(
                    ExceptionUseCaseResponse.JWT_TOKEN_INVALID.getCode(),
                    ExceptionUseCaseResponse.JWT_TOKEN_INVALID.getMessage()
            ));
        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
