package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

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
    public Map<String,Object> getAllBackonInterface(@RequestParam("page") Integer crrPage, @RequestParam("size") Integer size){
        try {
            IPage<BackonInterface> page = backonInterfaceService.page(new Page<>(crrPage, size));
            return  ResultUtil.createResult(page);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @GetMapping("/detail")
    public Map<String,Object> getBackonInterface(@RequestParam("id")String id){
        try{
            BackonInterface backonInterface = backonInterfaceService.getById(id);
            return  ResultUtil.createResult(backonInterface);
        }catch (Exception e){
            return  ResultUtil.exceptionResult();
        }
    }

    @GetMapping("/delete")
    public Map<String,Object> deleteBackonInterface(@RequestParam("id")String id){
        try{
            boolean b = backonInterfaceService.removeById(id);
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
    public Map<String,Object> deleteBackonInterfaceByIds(@RequestParam("ids")String ids){
        try{
            boolean b = backonInterfaceService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Map<String,Object> createBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean save = backonInterfaceService.save(backonInterface);
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
    public Map<String,Object> updateBackonInterface(@RequestBody BackonInterface backonInterface){
        try{
            boolean b = backonInterfaceService.updateById(backonInterface);
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
