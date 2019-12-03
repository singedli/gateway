package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:09
 * @Description:
 */
@RestController
@RequestMapping("/gateway/interface")
public class GatewayInterfaceController {

    @Autowired
    private IGatewayInterfaceService gatewayInterfaceService;

    @GetMapping("/all")
    public Map<String,Object> getAllGatewayInterface(@RequestParam("page") Integer crrPage, @RequestParam("size") Integer size){
        try {
            IPage<GatewayInterface> page = gatewayInterfaceService.page(new Page<>(crrPage, size));
            return  ResultUtil.createResult(page);
        }catch (Exception e){
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

    @GetMapping("/delete")
    public Map<String,Object> deleteGatewayInterface(@RequestParam("id")String id){
        try{
            boolean b = gatewayInterfaceService.removeById(id);
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
