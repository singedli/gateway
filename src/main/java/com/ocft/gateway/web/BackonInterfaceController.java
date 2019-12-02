package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.service.IBackonInterfaceService;
import com.ocft.gateway.utils.ResultVOUtil;
import com.ocft.gateway.web.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultVO getAllBackonInterface(@RequestParam("page") Integer crrPage, @RequestParam("size") Integer size){
        IPage<BackonInterface> page = backonInterfaceService.page(new Page<>(crrPage, size));
        return ResultVOUtil.success(page);
    }
}
