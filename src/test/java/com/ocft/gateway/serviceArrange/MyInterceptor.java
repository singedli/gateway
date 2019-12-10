package com.ocft.gateway.serviceArrange;

import io.seata.saga.engine.exception.EngineExecutionException;
import io.seata.saga.engine.pcext.StateHandlerInterceptor;
import io.seata.saga.proctrl.ProcessContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lijiaxing
 * @Title: MyInterceptor
 * @ProjectName Demo
 * @date 2019/12/9下午5:10
 * @Description:
 */
@Component
public class MyInterceptor implements StateHandlerInterceptor {
    @Override
    public void preProcess(ProcessContext context) throws EngineExecutionException {
        Map<String, Object> variables = context.getVariables();
        System.err.println(variables);
    }

    @Override
    public void postProcess(ProcessContext context, Exception e) throws EngineExecutionException {

    }
}
