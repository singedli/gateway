package com.ocft.gateway.web;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.ResultUtil;
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
@RestController
@RequestMapping("/cache/config")
public class GatewayCacheController {

    @Autowired
    private IGatewayCacheService gatewayCacheService;

    @PostMapping(value = "/all",produces = "application/json; charset=utf-8")
    public Map<String,Object> getAllGatewayCache(@RequestBody Map<String,Object> body){
        try {
            Integer current = (Integer) body.get("current");
            Integer size = (Integer) body.get("size");

            QueryWrapper<GatewayCache> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(body.get("name"))){
                queryWrapper.eq("name",body.get("name"));
            }

            if (!StringUtils.isEmpty(body.get("url"))){
                queryWrapper.eq("url",body.get("url"));
            }

            if (!StringUtils.isEmpty(body.get("backonUrl"))){
                queryWrapper.eq("backonUrl",body.get("backonUrl"));
            }

            if (!StringUtils.isEmpty(body.get("status"))){
                queryWrapper.eq("status",body.get("status"));
            }

            if (!StringUtils.isEmpty(body.get("resultNum"))){
                queryWrapper.eq("resultNum",body.get("resultNum"));
            }

            if (!StringUtils.isEmpty(body.get("expireTime"))){
                queryWrapper.eq("expireTime",body.get("expireTime"));
            }

            if (!StringUtils.isEmpty(body.get("requestBody"))){
                queryWrapper.eq("requestBody",body.get("requestBody"));
            }

            if (!StringUtils.isEmpty(body.get("responseBody"))){
                queryWrapper.eq("responseBody",body.get("responseBody"));
            }

            if (!StringUtils.isEmpty(body.get("createTime"))){
                queryWrapper.le("createTime",body.get("createTime"));
            }

            if (!StringUtils.isEmpty(body.get("updateTime"))){
                queryWrapper.le("updateTime",body.get("updateTime"));
            }
            IPage<GatewayCache> gatewayCacheIPage = gatewayCacheService.page(new Page<>(current,size),queryWrapper);
            return  ResultUtil.createResult(gatewayCacheIPage);
        }catch (Exception e){
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
