package org.uts.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 注册发现 接口类
 * @Author codBoy
 * @Date 2024/7/10 21:01
 */
@RestController
public class DiscoveryController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/instance")
    public String getInstance() {

        return "SUCCESS";
    }
}
