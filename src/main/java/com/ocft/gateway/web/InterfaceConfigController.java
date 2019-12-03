package com.ocft.gateway.web;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.ocft.gateway.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-02 16:30
 * @description: 请求接口防刷配置表
 **/
@RestController
@RequestMapping("/interfaceConfig")
public class InterfaceConfigController {

    private static final Logger logger = LoggerFactory.getLogger(InterfaceConfigController.class);


    @Autowired
    IInterfaceConfigService iInterfaceConfigService;


    //分页查询所有
    @RequestMapping("/getPage")
    public Map<String, Object> findAllRequestType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        try {
            return ResultUtil.createResult(iInterfaceConfigService.getPage(pageNum, pageSize));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "分页查询防刷参数出错"));
        }
    }

    //分页查询所有
    @RequestMapping("/findById")
    public Map<String, Object> findAllRequestType(@RequestParam("id") String id) {
        try {
            return ResultUtil.createResult(iInterfaceConfigService.getById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询防刷详情出错"));
        }
    }


    //修改
    @RequestMapping("/updateById")
    public Map<String, Object> findAllRequestType(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            iInterfaceConfigService.updateById(interfaceConfig);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "修改防刷详情出错"));
        }
    }

    //新增
    @RequestMapping("/addOne")
    public Map<String, Object> addOne(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            iInterfaceConfigService.save(interfaceConfig);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "新增接口配置出错"));
        }
    }

    //批量删除
    @RequestMapping("/deleteByIds")
    public Map<String, Object> deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultUtil.createResult(null);
        }
        try {
            String[] idArr = ids.split(",");
            iInterfaceConfigService.removeByIds(Arrays.asList(idArr));
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "删除接口配置出错"));
        }
    }

}
