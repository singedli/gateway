package com.ocft.gateway.web;

import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 14:43
 * @Description:
 */
@RestController
@RequestMapping("/cache")
public class GatewayCacheController {

    @Autowired
    private IGatewayCacheService gatewayCacheService;

    @GetMapping("/all")
    public ResultVO getAllGatewayCache(){


        return  ResultVOUtil.success();
    }
}
