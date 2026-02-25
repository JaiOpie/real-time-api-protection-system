package com.project.api_gateway.filter;

import com.project.api_gateway.constants.ApiGatewayConstant;
import com.project.api_gateway.event.ApiRequestEvent;
import com.project.api_gateway.producer.ApiRequestProducer;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter implements GlobalFilter {

    private final ApiRequestProducer apiRequestProducer;

    public ApiKeyFilter(ApiRequestProducer apiRequestProducer) {
        this.apiRequestProducer = apiRequestProducer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String apikey = exchange.getRequest()
                        .getHeaders().getFirst(ApiGatewayConstant.API_KEY_HEADER);

        if(apikey==null || !apikey.equals(ApiGatewayConstant.VALID_API_KEY)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        long startTime = System.currentTimeMillis();
        return chain.filter(exchange).then(
                Mono.fromRunnable(()->{
                    long responseTime = System.currentTimeMillis()-startTime;
                    ApiRequestEvent event = new ApiRequestEvent(
                            apikey,
                            exchange.getRequest().getPath().toString(),
                            exchange.getRequest().getMethod().name(),
                            exchange.getResponse().getStatusCode().value(),
                            responseTime,
                            System.currentTimeMillis()
                    );
                    apiRequestProducer.sendEvent(event);
                })
        );
    }
}
