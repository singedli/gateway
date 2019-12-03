package com.ocft.gateway.web;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.service.IGatewayCacheService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResultVO getAllGatewayCache(@RequestParam("page") Integer crrPage,@RequestParam("size") Integer size){
        try {
            IPage<GatewayCache> page = gatewayCacheService.page(new Page<>(crrPage, size));
            return  ResultVOUtil.success(page);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存查询异常");
        }
    }

    @GetMapping("/detail")
    public ResultVO getGatewayCache(@RequestParam("id")String id){
        try{
            GatewayCache gatewayCache = gatewayCacheService.getById(id);
            return  ResultVOUtil.success(gatewayCache);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存明细查询异常");
        }
    }

    @GetMapping("/delete")
    public ResultVO deleteGatewayCache(@RequestParam("id")String id){
        try{
            boolean b = gatewayCacheService.removeById(id);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"缓存删除失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存删除失败");
        }
    }

    @GetMapping("/delete/ids")
    public ResultVO deleteGatewayCacheByIds(@RequestParam("ids")String ids){
        try{
            boolean b = gatewayCacheService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"缓存批量删除失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存批量删除失败");
        }
    }

    @PostMapping("/create")
    public ResultVO createGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean save = gatewayCacheService.save(gatewayCache);
            if (save){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"缓存配置新增失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存配置新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO updateGatewayCache(@RequestBody GatewayCache gatewayCache){
        try{
            boolean b = gatewayCacheService.updateById(gatewayCache);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"缓存配置更新失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"缓存配置更新失败");
        }
    }
}
