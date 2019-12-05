package com.ocft.gateway.web;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.QueryInterfaceRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /*@Autowired
    RedisUtil redisUtil;*/

    //分页查询
    @PostMapping("/getPage")
    public Map<String, Object> findAllRequestType(@RequestBody QueryInterfaceRequest queryInterfaceRequest) {
        try {
            return ResultUtil.createResult(iInterfaceConfigService.queryInterfaceConfigs(queryInterfaceRequest));
        } catch (Exception e) {
            logger.error("分页查询限流配置发生异常:{}",e);
            return ResultUtil.bizExceptionResult(new GatewayException("500", "分页查询防刷参数出错"));
        }
    }

    //根据id查询
    @PostMapping("/findById")
    public Map<String, Object> findAllRequestType(String id) {
        try {
            return ResultUtil.createResult(iInterfaceConfigService.getById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询防刷详情出错"));
        }
    }


    //修改
    @PostMapping("/updateById")
    public Map<String, Object> updateById(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            iInterfaceConfigService.modifyById(interfaceConfig);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "修改防刷详情出错"));
        }
    }

    //新增
    @PostMapping("/addOne")
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
    @PostMapping("/deleteByIds")
    public Map<String, Object> deleteByIds(@RequestBody Map<String,Object> body) {
        if (StringUtils.isBlank((String)body.get("ids"))) {
            return ResultUtil.createResult(null);
        }
        try {
            String ids =(String)body.get("ids");
            String[] idArr = ids.split(",");
            iInterfaceConfigService.removeByIds(Arrays.asList(idArr));
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "删除接口配置出错"));
        }
    }


  /*  //**
    @PostMapping("/removeKey")
    public void removeKey(HttpServletRequest request){
        String realIp = WebUtil.getRealIp(request);
        Boolean exists = redisUtil.exists(realIp);
        Boolean delete = redisUtil.delete(realIp);
        System.out.println(delete);
    }
*/
}
