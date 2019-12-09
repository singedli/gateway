package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.BackonInterfaceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:10
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/backon/interface")
public class BackonInterfaceController {

    @Autowired
    private IBackonInterfaceService backonInterfaceService;

    @PostMapping("/all")
    public Map<String,Object> getAllBackonInterface(@RequestBody BackonInterfaceRequest request){
        try {
            IPage<BackonInterface> page = backonInterfaceService.getBackonInterfaceList(request);
            return  ResultUtil.createResult(page);
        }catch (Exception e){
            log.error("查询后台接口配置列表发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/detail")
    public Map<String,Object> getBackonInterface(@RequestBody BackonInterfaceRequest request){
        try{
            BackonInterface backonInterface = backonInterfaceService.getById(request.getId());
            return  ResultUtil.createResult(backonInterface);
        }catch (Exception e){
            log.error("查询后台接口配置明细发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/delete")
    public Map<String,Object> deleteBackonInterface(@RequestBody BackonInterfaceRequest request){
        try{
            boolean b = backonInterfaceService.removeById(request.getId());
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除后台接口配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除后台接口配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/delete/ids")
    public Map<String,Object> deleteBackonInterfaceByIds(@RequestBody Map<String,Object> body){
        try{
            String ids = body.get("ids").toString();
            boolean b = backonInterfaceService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除后台接口配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除后台接口配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/create")
    public Map<String,Object> createBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean save = backonInterfaceService.save(backonInterface);
            if (save){
                return  ResultUtil.createResult(null);
            }else {
                log.error("新增后台接口配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("新增后台接口配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping("/update")
    public Map<String,Object> updateBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean b = backonInterfaceService.updateById(backonInterface);
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("修改后台接口配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("修改后台接口配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }
}
