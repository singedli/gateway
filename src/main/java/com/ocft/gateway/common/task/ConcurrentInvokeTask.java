package com.ocft.gateway.common.task;

import lombok.Data;
import lombok.experimental.Accessors;

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
public class ConcurrentInvokeTask implements Callable<String> {

    private String url;
    private String header;
    private String requestBody;
    private String httpMethod;
    private CountDownLatch latch;

    @Override
    public String call() throws Exception {

        return null;
    }


}
