package com.ocft.gateway.web;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.QueryGatewayCacheRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 14:43
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/cache/config")
public class GatewayCacheController {

    @Autowired
    private IGatewayCacheService gatewayCacheService;

    @PostMapping(value = "/all")
    public Map<String,Object> getGatewayCacheList(@RequestBody QueryGatewayCacheRequest request){
        try {
            IPage<GatewayCache> gatewayCacheIPage = gatewayCacheService.getGatewayCacheList(request);
            return  ResultUtil.createResult(gatewayCacheIPage);
        }catch (Exception e){
            log.error("查询网关缓存列表发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/detail", produces = "application/json; charset=utf-8")
    public Map<String,Object> getGatewayCache(@RequestBody Map<String,Object> body){
        try{
            String id = body.get("id").toString();
            GatewayCache gatewayCache = gatewayCacheService.getById(id);
            return  ResultUtil.createResult(gatewayCache);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete",produces = "application/json; charset=utf-8")
    public Map<String,Object> deleteGatewayCache(@RequestBody Map<String,Object> body){
        try{
            String id = body.get("id").toString();
            boolean b = gatewayCacheService.removeById(id);
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete/ids", produces = "application/json; charset=utf-8")
    public Map<String,Object> deleteGatewayCacheByIds(@RequestBody Map<String,Object> body){
        try{
            String ids = body.get("ids").toString();
            boolean b = gatewayCacheService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/create",produces = "application/json; charset=utf-8")
    public Map<String,Object> createGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean save = gatewayCacheService.save(gatewayCache);
            if (save){
                return  ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/update",produces = "application/json; charset=utf-8")
    public Map<String,Object> updateGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean b = gatewayCacheService.updateById(gatewayCache);
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }
}
