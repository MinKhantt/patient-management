package com.minkhant.apigateway.config;

import com.minkhant.apigateway.filter.JwtValidationGatewayFilterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {

    private final JwtValidationGatewayFilterFactory jwtValidation;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient-service-route", r -> r
                        .path("/api/patients", "/api/patients/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(jwtValidation.apply(new Object()))
                        )
                        .uri("http://patient-service:4000"))

                .route("api-docs-patient-route", r -> r
                        .path("/api-docs/patients")
                        .filters(f -> f.rewritePath("/api-docs/patients", "/v3/api-docs"))
                        .uri("http://patient-service:4000"))

                .route("auth-service-route", r -> r
                        .path("/api/auth", "/api/auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://auth-service:4005"))

                .route("api-docs-auth-route", r -> r
                        .path("/api-docs/auth")
                        .filters(f -> f.rewritePath("/api-docs/auth", "/v3/api-docs"))
                        .uri("http://auth-service:4005"))
                .build();
    }
}
