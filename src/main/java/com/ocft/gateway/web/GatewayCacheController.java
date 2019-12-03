package com.ocft.gateway.web;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public Map<String,Object> getAllGatewayCache(@RequestParam("page") Integer crrPage, @RequestParam("size") Integer size){
        try {
            IPage<GatewayCache> page = gatewayCacheService.page(new Page<>(crrPage, size));
            return  ResultUtil.createResult(page);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @GetMapping("/detail")
    public Map<String,Object> getGatewayCache(@RequestParam("id")String id){
        try{
            GatewayCache gatewayCache = gatewayCacheService.getById(id);
            return  ResultUtil.createResult(gatewayCache);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @GetMapping("/delete")
    public Map<String,Object> deleteGatewayCache(@RequestParam("id")String id){
        try{
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

    @GetMapping("/delete/ids")
    public Map<String,Object> deleteGatewayCacheByIds(@RequestParam("ids")String ids){
        try{
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

    @PostMapping("/create")
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

    @PostMapping("/update")
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
