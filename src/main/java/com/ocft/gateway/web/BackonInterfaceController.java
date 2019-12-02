package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:10
 * @Description:
 */
@RestController
@RequestMapping("/backon/interface")
public class BackonInterfaceController {

    @Autowired
    private IBackonInterfaceService backonInterfaceService;

    @GetMapping("/all")
    public ResultVO getAllBackonInterface(@RequestParam("page") Integer crrPage,@RequestParam("size") Integer size){
        try {
            IPage<BackonInterface> page = backonInterfaceService.page(new Page<>(crrPage, size));
            return  ResultVOUtil.success(page);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"后台服务接口查询异常");
        }
    }

    @GetMapping("/detail")
    public ResultVO getBackonInterface(@RequestParam("id")String id){
        try{
            BackonInterface backonInterface = backonInterfaceService.getById(id);
            return  ResultVOUtil.success(backonInterface);
        }catch (Exception e){
            return  ResultVOUtil.error(500,"后台服务接口明细查询异常");
        }
    }

    @GetMapping("/delete")
    public ResultVO deleteBackonInterface(@RequestParam("id")String id){
        try{
            boolean b = backonInterfaceService.removeById(id);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"后台服务接口删除失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"后台服务接口删除失败");
        }
    }

    @PostMapping("/create")
    public ResultVO createBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean save = backonInterfaceService.save(backonInterface);
            if (save){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"后台服务接口配置新增失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"后台服务接口配置新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO updateBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean b = backonInterfaceService.updateById(backonInterface);
            if (b){
                return  ResultVOUtil.success();
            }else {
                return  ResultVOUtil.error(500,"后台服务接口配置更新失败");
            }
        }catch (Exception e){
            return  ResultVOUtil.error(500,"后台服务接口配置更新失败");
        }
    }
}
