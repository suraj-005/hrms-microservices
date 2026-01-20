package com.hrms.api_gateway.utils;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ Public endpoints
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return forbidden(exchange);
        }

        String token = authHeader.substring(7);

        Claims claims;
        try {
            claims = jwtUtil.getClaims(token);
        } catch (Exception e) {
            return forbidden(exchange);
        }

        // 🔑 Extract roles from JWT
        List<String> roles = claims.get("roles", List.class);
        String employeeId = claims.get("employeeId").toString();

        if (path.startsWith("/employees")
                && !hasAnyRole(roles, "ROLE_ADMIN", "ROLE_HR")) {
            return forbidden(exchange);
        }

        // Attendance APIs → EMPLOYEE or HR
        if (path.startsWith("/attendance")
                && !hasAnyRole(roles, "ROLE_EMPLOYEE", "ROLE_HR")) {
            return forbidden(exchange);
        }

        // Payroll APIs → ADMIN only
        if (path.startsWith("/payroll")
                && !hasAnyRole(roles, "ROLE_ADMIN")) {
            return forbidden(exchange);
        }

        ServerHttpRequest mutatedRequest =
                exchange.getRequest()
                        .mutate()
                        .header("X-Employee-Id", employeeId)
                        .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean hasAnyRole(List<String> roles, String... allowedRoles) {
        return roles != null &&
                Arrays.stream(allowedRoles).anyMatch(roles::contains);
    }

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
