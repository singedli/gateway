package com.ocft.gateway.serviceArrange;

import io.seata.saga.engine.exception.EngineExecutionException;
import io.seata.saga.engine.pcext.interceptors.ServiceTaskHandlerInterceptor;
import io.seata.saga.proctrl.ProcessContext;
import org.springframework.stereotype.Component;

/**
 * @author lijiaxing
 * @Title: MyInterceptor2
 * @ProjectName gateway
 * @date 2019/12/9下午6:08
 * @Description: TODO
 */
@Component
public class MyInterceptor2 extends ServiceTaskHandlerInterceptor {
    @Override
    public void preProcess(ProcessContext context) throws EngineExecutionException {
//        super.preProcess(context);
//        System.err.println("111");
    }
}
