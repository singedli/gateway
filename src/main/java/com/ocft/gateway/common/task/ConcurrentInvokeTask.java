package com.ocft.gateway.common.task;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.utils.HttpUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author lijiaxing
 * @Title: ConcurrentInvokeTask
 * @ProjectName gateway
 * @date 2019/11/24上午2:09
 * @Description: 并发调用接口任务
 */
@Data
@Accessors(chain=true)
public class ConcurrentInvokeTask implements Runnable {
    private String url;
    private Map<String,String> headers;
    private String requestBody;
    private String httpMethod;
    private Map<String,Object> getParams;
    private CountDownLatch latch;
    private JSONObject res;

    @Override
    public void run() {
        String result = null;
        try {
            if (HttpMethod.POST.matches(httpMethod)) {
                result = HttpUtil.postJsonParams(url, requestBody, headers);
            } else if (HttpMethod.GET.matches(httpMethod)) {
                result = HttpUtil.get(url, getParams, headers);
            } else {
                throw new GatewayException(ResponseEnum.HTTP_METHOD_NOT_EXIST_SUPPORTED);
            }
            res = JSONObject.parseObject(result);
        }catch (Exception e){
            //抛业务异常
        }finally {
            latch.countDown();
        }
    }
}
