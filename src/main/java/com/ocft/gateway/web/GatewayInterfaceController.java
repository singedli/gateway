package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IGatewayInterfaceService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResultVO getAllGatewayInterface(@RequestParam("page") Integer crrPage,@RequestParam("size") Integer size){
        try {
            IPage<GatewayInterface> page = gatewayInterfaceService.page(new Page<>(crrPage, size));
            return  ResultVOUtil.success(page);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口查询异常");
        }
    }

    @GetMapping("/detail")
    public ResultVO getGatewayInterface(@RequestParam("id")String id){
        try{
            GatewayInterface gatewayInterface = gatewayInterfaceService.getById(id);
            return  ResultVOUtil.success(gatewayInterface);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口明细查询异常");
        }
    }

    @GetMapping("/delete")
    public ResultVO deleteGatewayInterface(@RequestParam("id")String id){
        try{
            boolean b = gatewayInterfaceService.removeById(id);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"网关接口删除失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口删除失败");
        }
    }

    @GetMapping("/delete/ids")
    public ResultVO deleteGatewayInterfaceByIds(@RequestParam("ids")String ids){
        try{
            boolean b = gatewayInterfaceService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"网关接口批量删除失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口批量删除失败");
        }
    }

    @PostMapping("/create")
    public ResultVO createGatewayInterface(@RequestBody GatewayInterface gatewayInterface){
        try{
            boolean save = gatewayInterfaceService.save(gatewayInterface);
            if (save){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"网关接口配置新增失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口配置新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO updateGatewayInterface(@RequestBody GatewayInterface gatewayInterface){
        try{
            boolean b = gatewayInterfaceService.updateById(gatewayInterface);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"网关接口配置更新失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"网关接口配置更新失败");
        }
    }
}
