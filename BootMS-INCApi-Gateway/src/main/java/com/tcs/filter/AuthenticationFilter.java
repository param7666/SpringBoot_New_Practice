package com.tcs.filter;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.tcs.config.RouteValidator;
import com.tcs.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator routeValidator, JwtUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!routeValidator.isSecured.test(request)) {
                // Public endpoint, skip validation
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsHeader("Authorization")) {
                return onError(exchange, "Missing Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
            String token = authHeader.startsWith("Bearer ")
                    ? authHeader.substring(7)
                    : authHeader;

            if (!jwtUtil.isTokenValid(token)) {
                return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
            }

            Claims claims = jwtUtil.extractClaims(token);
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            Long userId = claims.get("userId", Long.class);

            // Forward identity to downstream services via headers
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Auth-Username", username)
                    .header("X-Auth-Role", role)
                    .header("X-Auth-UserId", String.valueOf(userId))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }



    private Mono<Void> onError(org.springframework.web.server.ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", "application/json");
        String body = String.format("{\"error\": \"%s\"}", message);
        org.springframework.core.io.buffer.DataBuffer buffer =
                response.bufferFactory().wrap(body.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
        // Add filter-specific config properties here if needed later
    }
}