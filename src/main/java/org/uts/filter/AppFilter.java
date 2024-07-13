package org.uts.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 定义网关的全局过滤器
 * */

@Component
@Slf4j
public class AppFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("request URL: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }

}
