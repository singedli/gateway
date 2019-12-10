package com.ocft.gateway.serviceArrange;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.evaluator.JsonOperateEvalutor;
import com.ocft.gateway.sao.AbstractBaseSao;
import com.ocft.gateway.spring.SpringContextHolder;
import com.ocft.gateway.web.dto.InvokeThirdDTO;
import com.ocft.gateway.web.response.HttpResponseModel;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.DomainConstants;
import io.seata.saga.statelang.domain.StateMachineInstance;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijiaxing
 * @Title: StateMachine
 * @ProjectName gateway
 * @date 2019/12/9下午2:07
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = "GatewayApplication")// 指定启动类
public class StateMachine {

    @Autowired
    private AbstractBaseSao sao;

    @Autowired
    private StateMachineEngine engine;

    @Autowired
    private SpringContextHolder holder;
//
// public static void main(String[] args) {
//        AbstraccBaseSao sao = new DefaultInvokeSaoImpl();
//        InvokeThirdDTO params = new InvokeThirdDTO();
//        params.setBackOnUrl("https://portal-test.medsci.cn/top/weekTop");
//        params.setMethod("POST");
//        params.setRequestData("{\"transNo\":\"dd12345678901\"}");
//        params.setCode("00000000");
//        HttpResponseModel<Object> objectHttpResponseModel = sao.invokeHandler(params);
//        String data = (String)objectHttpResponseModel.getData();
//        System.out.println(data);
//    }

    @Test
    public void test() {
        InvokeThirdDTO params = new InvokeThirdDTO();
        params.setBackOnUrl("https://portal-test.medsci.cn/top/weekTop");
        params.setMethod("POST");
        params.setRequestData("{\"transNo\":\"dd12345678901\"}");
        params.setCode("00000000");
        HttpResponseModel<Object> objectHttpResponseModel = sao.invokeHandler(params);
        JSONArray jsonArray = (JSONArray) objectHttpResponseModel.getData();
        System.out.println(jsonArray.toJSONString());
        JSONObject jsonObj = jsonArray.getJSONObject(0);
        String path = "moduleId";
        Integer jsonPropertyValue = (Integer) JsonOperateEvalutor.getJsonPropertyValue(jsonObj, path);
        System.out.println("jsonPropertyValue-->" + jsonPropertyValue);

        InvokeThirdDTO params2 = new InvokeThirdDTO();
        params2.setBackOnUrl("https://portal-test.medsci.cn/portal-article/getArticleById");
        params2.setMethod("POST");
        params2.setRequestData("{\"id\":" + jsonPropertyValue + "}");
        params2.setCode("00000000");
        HttpResponseModel<Object> res = sao.invokeHandler(params2);
        JSONObject jSONObject = (JSONObject) res.getData();
        System.out.println(jSONObject.toJSONString());
    }

    @Test
    public void test3() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, String> hashMap1 = new HashMap<>();

        hashMap1.put("backOnUrl", "https://portal-test.medsci.cn/top/weekTop");
        hashMap1.put("requestData", "{\"transNo\":\"dd12345678901\"}");
        hashMap1.put("code", "00000000");
        map1.put("FirstState",hashMap1);

        Map<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("backOnUrl", "https://portal-test.medsci.cn/portal-article/getArticleById");
        hashMap2.put("code", "00000000");
        map1.put("SecondState",hashMap2);

        StateMachineInstance machine = engine.start("invokeTestStateMachine", null, map1);
        Map<String, Object> endParams = machine.getEndParams();
        System.err.println(JSONObject.toJSONString(endParams));
    }
}