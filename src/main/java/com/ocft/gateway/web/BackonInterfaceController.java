package com.ocft.gateway.web;

import com.ocft.gateway.service.IBackonInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:10
 * @Description:
 */
@RestController
@RequestMapping("/backon")
public class BackonInterfaceController {

    @Autowired
    private IBackonInterfaceService backonInterfaceService;
}
