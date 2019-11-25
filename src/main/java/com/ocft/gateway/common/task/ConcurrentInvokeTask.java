package com.ocft.gateway.common.task;

import java.util.concurrent.Callable;

/**
 * @author lijiaxing
 * @Title: ConcurrentInvokeTask
 * @ProjectName gateway
 * @date 2019/11/24上午2:09
 * @Description: 并发调用接口任务
 */
public class ConcurrentInvokeTask implements Callable<String> {

    private String url;
    private String header;
    private String requestBody;
    private String httpMethod;

    @Override
    public String call() throws Exception {

        return null;
    }


}
