package com.ocft.gateway.web;

import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.ocft.gateway.service.IRequestTypeService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    public ResultVO findAllRequestType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        try {
            return ResultVOUtil.success(iInterfaceConfigService.getPage(pageNum, pageSize));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "分页查询防刷参数出错");
        }
    }

    //分页查询所有
    @RequestMapping("/findById")
    public ResultVO findAllRequestType(@RequestParam("id") String id) {
        try {
            return ResultVOUtil.success(iInterfaceConfigService.getById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "查询防刷详情出错");
        }
    }


    //修改
    @RequestMapping("/updateById")
    public ResultVO findAllRequestType(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            return ResultVOUtil.success(iInterfaceConfigService.updateById(interfaceConfig));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "修改防刷详情出错");
        }
    }

    //新增
    @RequestMapping("/addOne")
    public ResultVO addOne(@RequestBody InterfaceConfig interfaceConfig) {
        try {
            iInterfaceConfigService.save(interfaceConfig);
            return ResultVOUtil.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "添加防刷详情出错");
        }
    }

    //批量删除
    @RequestMapping("/deleteByIds")
    public ResultVO deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultVOUtil.success();
        }
        try {
            String[] idArr = ids.split(",");
            return ResultVOUtil.success(iInterfaceConfigService.removeByIds(Arrays.asList(idArr)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultVOUtil.error(500, "添加防刷详情出错");
        }
    }

}
