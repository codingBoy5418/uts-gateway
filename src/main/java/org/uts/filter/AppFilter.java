package org.uts.filter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.uts.utils.IPUtils;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * 定义网关的全局过滤器
 * */

@Component
@Slf4j
public class AppFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //前置处理
        preHandler(exchange, chain);

        Mono<Void> res = chain.filter(exchange);

        //后置处理
        postHandler(exchange, chain);

        return res;
    }

    public void preHandler(ServerWebExchange exchange, GatewayFilterChain chain){
        log.info("request-url: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI());

        //将客户端IP设置到请求头中，传递到微服务
        exchange.getRequest().mutate().header("Real-IP", IPUtils.getIpAddress(exchange.getRequest()));

        //设置请求时间
        ContextHolder.appContext.set(System.currentTimeMillis());
    }

    public void postHandler(ServerWebExchange exchange, GatewayFilterChain chain){
        //打印请求路径信息
        printRequestUrlInfo(exchange);
    }

    /**
     * 打印请求路径信息
     * */
    public void printRequestUrlInfo(ServerWebExchange exchange){
        Long preTime = (Long)ContextHolder.appContext.get();
        Long postTime = System.currentTimeMillis();
        long costTime = (postTime - preTime) / 1000;
        if(costTime > 3){
            log.info("request-url: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI() + " request takes too long, time: " + costTime + " seconds ...");
        }
        else {
            log.info("request-url: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI() + " return ...");
        }
    }

}
