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

    @PostMapping(value = "/detail")
    public Map<String,Object> getGatewayCache(@RequestBody QueryGatewayCacheRequest request){
        try{
            GatewayCache gatewayCache = gatewayCacheService.getById(request.getId());
            return  ResultUtil.createResult(gatewayCache);
        }catch (Exception e){
            log.error("查询网关缓存明细发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete")
    public Map<String,Object> deleteGatewayCache(@RequestBody QueryGatewayCacheRequest request){
        try{
            boolean b = gatewayCacheService.removeById(request.getId());
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除网关缓存发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除网关缓存发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete/ids")
    public Map<String,Object> deleteGatewayCacheByIds(@RequestBody Map<String,Object> body){
        try{
            String ids = body.get("ids").toString();
            boolean b = gatewayCacheService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除网关缓存发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除网关缓存发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/create")
    public Map<String,Object> createGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean save = gatewayCacheService.save(gatewayCache);
            if (save){
                return  ResultUtil.createResult(null);
            }else {
                log.error("新增网关缓存发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("新增网关缓存发生异常:{}",e);
            return ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/update")
    public Map<String,Object> updateGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean b = gatewayCacheService.updateById(gatewayCache);
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("修改网关缓存发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("修改网关缓存发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }
}
