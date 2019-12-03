package com.ocft.gateway.web;

import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.service.IBackonService;
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
 * @create: 2019-12-03 11:37
 * @description:
 **/
@RestController
@RequestMapping("/backon")
public class BackonController {

    private static final Logger logger = LoggerFactory.getLogger(BackonController.class);


    @Autowired
    IBackonService iBackonService;

    /**
     * 分页
     */

    @PostMapping("/getPage")
    public Map<String, Object> findAllRequestType(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        try {
            return ResultUtil.createResult(iBackonService.getPage(pageNum, pageSize));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "分页查询出错"));
        }
    }


    /**
     * 查询详情
     */
    @PostMapping("/findById")
    public Map<String, Object> findAllRequestType(@RequestParam("id") String id) {
        try {
            return ResultUtil.createResult(iBackonService.getById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "查询详情出错"));
        }
    }


    /**
     * 新增
     */

    @PostMapping("/addOne")
    public Map<String, Object> addOne(@RequestBody Backon backon) {
        try {
            iBackonService.save(backon);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "新增出错"));
        }
    }

    /**
     * 修改
     */

    @PostMapping("/updateById")
    public Map<String, Object> findAllRequestType(@RequestBody Backon backon) {
        try {
            iBackonService.updateById(backon);
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "修改出错"));
        }
    }

    /**
     * 删除
     */
    @PostMapping("/deleteByIds")
    public Map<String, Object> deleteByIds(@RequestParam("ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return ResultUtil.createResult(null);
        }
        try {
            String[] idArr = ids.split(",");
            iBackonService.removeByIds(Arrays.asList(idArr));
            return ResultUtil.createResult(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultUtil.bizExceptionResult(new GatewayException("500", "删除出错"));
        }
    }
}
