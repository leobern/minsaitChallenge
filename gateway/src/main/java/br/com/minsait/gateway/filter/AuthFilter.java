package br.com.minsait.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter, Ordered {


    private final WebClient webClient;
    @Value("${server.route.authorization.url}")
    private String authorizationBaseUrl;

    @Value("${server.route.authorization.validateToken}")
    private String validateTokenUri;


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            // Obtém o token de autenticação do cabeçalho
            String token = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

            // Valida o token chamando o serviço de autorização
            return webClient.get()
                .uri(authorizationBaseUrl+validateTokenUri)
                .header(HttpHeaders.AUTHORIZATION, token)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        // Token válido, continua para o próximo filtro
                        return response.bodyToMono(String.class)
                                .flatMap(authResponse -> {
                                    String authenticatedUser = authResponse;

                                    // Adiciona o usuário logado ao cabeçalho da solicitação
                                    exchange.getRequest().mutate()
                                            .header("X-Authenticated-User", authenticatedUser)
                                            .build();

                                    // Encaminha a solicitação para o próximo filtro
                                    return chain.filter(exchange);
                                });
                    } else {
                        // Token inválido, retorna resposta de erro
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                });
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }
    }