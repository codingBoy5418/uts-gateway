package org.uts.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * @Description IP地址请求类
 * @Author codBoy
 * @Date 2024/7/31 21:00
 */
@Slf4j
public class IPUtils {

    private static final String UNKNOWN = "UNKNOWN";

    /*
      获取客户端IP地址
     */
    public static String getIpAddress(ServerHttpRequest request)
    {
        String ipAddress = UNKNOWN;
        try {
            //获取IP
            ipAddress = request.getHeaders().getFirst("x-forwarded-for");
            if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
                ipAddress = request.getHeaders().getFirst("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("X-Forwarded-For");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("x-real-ip");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("X-Real-IP");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("host");
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst("Host");
            }

            //如果没有转发的ip，则取当前通信的请求端的ip
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                InetSocketAddress inetSocketAddress = request.getRemoteAddress();
                if(inetSocketAddress != null) {
                    ipAddress = inetSocketAddress.getAddress().getHostAddress();
                }
                return  "0:0:0:0:0:0:0:1".equals(ipAddress) ? "127.0.0.1" : ipAddress;
            }

        } catch (Exception e){
            log.error("Acquire IP Address from request fail !!!");
            ipAddress = "未知";
        }

        return ipAddress;
    }

}
