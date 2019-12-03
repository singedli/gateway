package com.ocft.gateway.web;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.RequestType;
import com.ocft.gateway.service.IRequestTypeService;
import com.ocft.gateway.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: Bobby
 * @create: 2019-12-02 15:10
 * @description: 请求类型更新接口
 **/
@RestController
@RequestMapping("/requestType")
public class RequestTypeController {


    private static final Logger logger = LoggerFactory.getLogger(RequestTypeController.class);

    @Autowired
    IRequestTypeService iRequestTypeService;

    //查询所有
    @PostMapping("/findAll")
    public Map<String, Object> findAllRequestType() {
        try {
            return ResultUtil.createResult(iRequestTypeService.findAllRequestType());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询请求类型出错"));
        }
    }

    //查询app类型
    @PostMapping("/findApp")
    public Map<String, Object> findAppRequestType() {
        try {
            return ResultUtil.createResult(iRequestTypeService.findTypeApp());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询客户端请求类型出错"));
        }

    }

    //查询浏览器类型
    @PostMapping("/findBrowser")
    public Map<String, Object> findBrowserRequestType() {
        try {
            return ResultUtil.createResult(iRequestTypeService.findTypeBrowser());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询浏览器请求类型出错"));
        }
    }

    //单个查询
    @PostMapping("/findOne")
    public Map<String, Object> findById(@RequestParam("id") String id) {
        try {
            RequestType byId = iRequestTypeService.findById(id);
            return ResultUtil.createResult(byId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询请求类型详情出错"));
        }
    }

    //根据id修改
    @PostMapping("/updateById")
    public Map<String, Object> updateById(@RequestBody RequestType requestType) {
        try {
            iRequestTypeService.updateById(requestType);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "修改请求类型出错"));
        }
    }

    //添加
    @PostMapping("/add")
    public Map<String, Object> addOne(@RequestBody RequestType requestType) {
        try {
            iRequestTypeService.save(requestType);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "新增请求类型出错"));
        }
    }

    //根据id删除
    @PostMapping("/deleteByIds")
    public Map<String, Object> deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultUtil.createResult(null);
        }
        try {
            String[] idArr = ids.split(",");
            iRequestTypeService.removeByIds(Arrays.asList(idArr));
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "删除请求类型出错"));
        }
    }
}
