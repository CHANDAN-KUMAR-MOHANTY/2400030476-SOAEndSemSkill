package com.banking.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("account-service-route", r -> r
                .path("/accounts", "/accounts/**")
                .filters(f -> f.rewritePath("/accounts(?<segment>/?.*)", "/internal/accounts${segment}"))
                .uri("http://localhost:8081"))
            .build();
    }
}
