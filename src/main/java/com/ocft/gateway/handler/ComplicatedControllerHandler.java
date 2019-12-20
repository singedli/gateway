package com.ocft.gateway.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.stateMachine.StateLangGenerator;
import com.ocft.gateway.utils.ResultUtil;
import com.ocft.gateway.web.response.HttpResponseModel;
import io.seata.saga.engine.StateMachineConfig;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.StateMachineInstance;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lijiaxing
 * @Title: ComplicatedControllerHandler
 * @ProjectName gateway
 * @date 2019/11/21下午6:22
 * @Description: 复杂类型处理器
 */
@Component
public class ComplicatedControllerHandler extends AbstractControllerHandler {

    private static final String RESULT_SUFFIX = "_result";


    @Autowired
    private StateMachineEngine engine;

    @Autowired
    private BackonInterfaceMapper backonInterfaceMapper;

    @Autowired
    private BackonMapper backonMapper;

    @Override
    public String buildReqHeader() {
        return null;
    }

    @Override
    public String buildReqBody(String body) {
        return body;
    }

    @Override
    public String sendToBacon(GatewayContext gatewayContext) {
        //获取状态机脚本
        GatewayInterface gatewayInterface = gatewayContext.getGatewayInterface();
        String url = gatewayInterface.getUrl();
        String stateMachineName = StateLangGenerator.safeEncodeStateUrl(url + "_state");
        //设置状态机参数
        String flowConfig = gatewayInterface.getFlowConfig();
        JSONObject flowConfigObject = JSONObject.parseObject(flowConfig);
        JSONArray edges = flowConfigObject.getJSONArray("edges");
        JSONArray nodes = flowConfigObject.getJSONArray("nodes");
        List<JSONObject> nodeList = nodes.toJavaList(JSONObject.class);

        Map<String, Object> params = new HashMap<>();
        LinkedList<String> nodeOrder = StateLangGenerator.buildList(edges);
        for (int i = 0; i < nodeOrder.size(); i++) {
            String id = nodeOrder.remove(i);
            JSONObject node = nodeList.stream().filter(n -> n.getString("id").equals(id)).findFirst().get();
            if ("task".equalsIgnoreCase(node.getString("stateType"))) {
                BackonInterface backonInterface = backonInterfaceMapper.selectOne(new QueryWrapper<BackonInterface>().eq("id", id).eq("status", 1).eq("is_deleted", "0"));
                String system = backonInterface.getSystem();
                String url1 = backonInterface.getUrl();
                Backon backon = backonMapper.selectOne(new QueryWrapper<Backon>().eq("system", system).eq("status", 1).eq("is_deleted", "0"));
                String backOnUrl = backon.getDomain() + backonInterface.getUrl() + backon.getSuffix();
                String requestData = gatewayContext.getRequestBody();
                Map<String, Object> param = new HashMap<>();
                param.put("backOnUrl", backOnUrl);
                if (i == 0) {
                    param.put("requestData", requestData);
                }
                String stateName = StateLangGenerator.safeEncodeStateUrl(url1 + "_task");
                params.put(stateName, param);
            }
        }
        //执行
        StateMachineInstance instance = engine.start(stateMachineName, null, params);
        Map<String, Object> endParams = instance.getEndParams();

        return decodeUrlForResp(endParams);
    }

    @Override
    public Map<String, Object> retToClient(String resout, HttpServletRequest request) {

        return ResultUtil.createResult(JSONObject.parseObject(resout));
    }

    private String decodeUrlForResp(Map<String, Object> data) {
        Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
        JSONObject newData = new JSONObject();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            Object value = data.get(key);
            if (key.endsWith(RESULT_SUFFIX)) {
                key = key.substring(0, key.length() - RESULT_SUFFIX.length());
                key = StateLangGenerator.getUrlFromEncodeStateName(key) + RESULT_SUFFIX;
            } else {
                key = StateLangGenerator.getUrlFromEncodeStateName(key);
            }
            iterator.remove();
            newData.put(key, value);
        }
        return JSONObject.toJSONString(newData, SerializerFeature.WriteMapNullValue);
    }


}
