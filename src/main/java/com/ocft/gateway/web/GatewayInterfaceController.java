package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.DeleteGatewayInterfaceRequest;
import com.ocft.gateway.web.dto.request.QueryGatewayInterfaceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:09
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/config/interface")
public class GatewayInterfaceController {

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

    @PostMapping("/all")
    public Map<String,Object> getAllGatewayInterface(@RequestBody QueryGatewayInterfaceRequest request){
        try {
            Page<GatewayInterface> gatewayInterfacePage = gatewayInterfaceService.queryGateWayInterfaces(request);
            return  ResultUtil.createResult(gatewayInterfacePage);
        }catch (Exception e){
            log.error("查询网关接口列表发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @GetMapping("/detail")
    public Map<String,Object> getGatewayInterface(@RequestParam("id")String id){
        try{
            GatewayInterface gatewayInterface = gatewayInterfaceService.getById(id);
            return  ResultUtil.createResult(gatewayInterface);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/delete")
    public Map<String,Object> deleteGatewayInterface(@RequestBody DeleteGatewayInterfaceRequest req){
        try{
            boolean b = gatewayInterfaceService.removeById(req.getId());
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
        return ResultUtil.successResult();
    }

    @GetMapping("/delete/ids")
    public Map<String,Object> deleteGatewayInterfaceByIds(@RequestParam("ids")String ids){
        try{
            boolean b = gatewayInterfaceService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return   ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/create")
    public Map<String,Object> createGatewayInterface(@RequestBody GatewayInterface gatewayInterface){
        try{
            boolean save = gatewayInterfaceService.save(gatewayInterface);
            if (save){
                return  ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/update")
    public Map<String,Object> updateGatewayInterface(@RequestBody GatewayInterface gatewayInterface){
        try{
            boolean b = gatewayInterfaceService.updateById(gatewayInterface);
            if (b){
                return   ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }
}
