package br.com.minsait.gateway.config;

import br.com.minsait.gateway.filter.AuthFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    @Value("${server.route.transactionManager.url}")
    String transactionManagerBaseUrl;
    @Value("${server.route.report.url}")
    String reportBaseUrl;

    @Value("${server.route.authorization.url}")
    String authorizationBaseUrl;

    private final AuthFilter authFilter;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r
                        .path("/auth/**")
                        .filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/${segment}"))
                        .uri(authorizationBaseUrl)  // URL do microserviço restapi
                )
            .route("transaction-manager", r -> r
                .path("/api/**")
                .filters(f ->{
                    f.rewritePath("/api/(?<segment>.*)", "/${segment}");
                    f
                    .filter(authFilter);
                return f;})  // Filtro personalizado para autenticação

                .uri(transactionManagerBaseUrl)  // URL do microserviço restapi
            )
                .route("report", r -> r
                        .path("/report/**")
                        .filters(f ->{
                            f.rewritePath("/report/(?<segment>.*)", "/${segment}");
                            f
                                    .filter(authFilter);
                            return f;})
                        .uri(reportBaseUrl)  // URL do microserviço restapi
                )
            .build();
    }



}
