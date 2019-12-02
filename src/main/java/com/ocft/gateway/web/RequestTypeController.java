package com.ocft.gateway.web;

import com.ocft.gateway.entity.RequestType;
import com.ocft.gateway.service.IRequestTypeService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    @RequestMapping("/findAll")
    public ResultVO findAllRequestType() {
        try {
            return ResultVOUtil.success(iRequestTypeService.findAllRequestType());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "查询请求类型出错");
        }
    }

    //查询app类型
    @RequestMapping("/findApp")
    public ResultVO findAppRequestType() {
        try {
            return ResultVOUtil.success(iRequestTypeService.findTypeApp());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "查询客户端请求类型出错");
        }

    }

    //查询浏览器类型
    @RequestMapping("/findBrowser")
    public ResultVO findBrowserRequestType() {
        try {
            return ResultVOUtil.success(iRequestTypeService.findTypeBrowser());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "查询浏览器请求类型出错");
        }
    }

    //单个查询
    @RequestMapping("/findOne")
    public ResultVO findById(@RequestParam("id") String id) {
        try {
            RequestType byId = iRequestTypeService.findById(id);
            return ResultVOUtil.success(byId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "查询请求类型出错:" + id);
        }
    }

    //根据id修改
    @PostMapping("/updateById")
    public ResultVO updateById(@RequestBody RequestType requestType) {
        try {
            iRequestTypeService.updateById(requestType);
            return ResultVOUtil.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "修改请求类型出错");
        }
    }

    //添加
    @PostMapping("/add")
    public ResultVO addOne(@RequestBody RequestType requestType) {
        try {
            iRequestTypeService.save(requestType);
            return ResultVOUtil.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "新增请求类型出错");
        }
    }

    //根据id删除
    @PostMapping("/deleteByIds")
    public ResultVO deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultVOUtil.success();
        }
        try {
            String[] idArr = ids.split(",");
            iRequestTypeService.removeByIds(Arrays.asList(idArr));
            return ResultVOUtil.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "删除请求类型出错");
        }
    }
}
