package com.ocft.gateway.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.service.IMessageConverterService;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.dto.request.MessageConverterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/8 17:04
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/message/converter")
public class MessageConverterController {
    @Autowired
    private IMessageConverterService messageConverterService;

    @PostMapping(value = "/all")
    public Map<String,Object> getMessageConverterList(@RequestBody MessageConverterRequest request){
        try {
            IPage<MessageConverter> messageConverterIPage = messageConverterService.getMessageConverterList(request);
            return  ResultUtil.createResult(messageConverterIPage);
        }catch (Exception e){
            log.error("查询报文转换配置列表发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/detail")
    public Map<String,Object> getMessageConverter(@RequestBody MessageConverterRequest request){
        try{
            MessageConverter messageConverter = messageConverterService.getById(request.getId());
            return  ResultUtil.createResult(messageConverter);
        }catch (Exception e){
            log.error("查询报文转换配置明细发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete")
    public Map<String,Object> deleteMessageConverter(@RequestBody MessageConverterRequest request){
        try{
            boolean b = messageConverterService.removeById(request.getId());
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除报文转换配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除报文转换配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/delete/ids")
    public Map<String,Object> deleteMessageConverterByIds(@RequestBody Map<String,Object> body){
        try{
            String ids = body.get("ids").toString();
            boolean b = messageConverterService.removeByIds(Arrays.asList(ids.split(",")));
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("删除报文转换配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("删除报文转换配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/create")
    public Map<String,Object> createMessageConverter(@RequestBody MessageConverter  messageConverter){
        try{
            boolean save = messageConverterService.save(messageConverter);
            if (save){
                return  ResultUtil.createResult(null);
            }else {
                log.error("新增报文转换配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("新增报文转换配置发生异常:{}",e);
            return ResultUtil.exceptionResult();
        }
    }

    @PostMapping(value = "/update")
    public Map<String,Object> updateMessageConverter(@RequestBody MessageConverter  messageConverter){
        try{
            boolean b = messageConverterService.updateById(messageConverter);
            if (b){
                return  ResultUtil.createResult(null);
            }else {
                log.error("修改报文转换配置发生异常");
                return  ResultUtil.exceptionResult();
            }
        }catch (Exception e){
            log.error("修改报文转换配置发生异常:{}",e);
            return  ResultUtil.exceptionResult();
        }
    }
}
