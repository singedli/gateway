package com.ocft.gateway.web;

import com.ocft.gateway.service.IGatewayInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:09
 * @Description:
 */
@RestController
@RequestMapping("/interface")
public class GatewayInterfaceController {

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceServicel;
}
