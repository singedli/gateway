package com.ocft.gateway.stateMachine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.entity.BackonInterface;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.entity.MessageConverter;
import com.ocft.gateway.enums.ResponseEnum;
import com.ocft.gateway.mapper.BackonInterfaceMapper;
import com.ocft.gateway.mapper.BackonMapper;
import com.ocft.gateway.mapper.MessageConverterMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lijiaxing
 * @Title: StateLangGenerator
 * @ProjectName gateway
 * @date 2019/12/13上午9:44
 * @Description: TODO
 */

@Component
public class StateLangGenerator {
    @Autowired
    private BackonInterfaceMapper backonInterfaceMapper;

    @Autowired
    private BackonMapper backonMapper;

    @Autowired
    private MessageConverterMapper messageConverterMapper;


    private static final String DEFAULT_STATE_MACHINE_VERSION = "0.0.2";
    private static final String STATE_MACHINE_SUFFIX = "的状态机";
    private static final String STATE_TASK = "task";
    private static final String STATE_CONVERTER = "converter";
    private static final String SUCCESS_STATE = "Succeed";
    private static final String TASK_TYPE = "ServiceTask";


    private static final String INVOKE_TASK_NAME_VALUE = "defaultInvokeOut";
    private static final String INVOKE_TASK_METHOD_VALUE = "invokeHandler";
    private static final String CONVERTER_TASK_NAME_VALUE = "paramsConverter";
    private static final String CONVERTER_TASK_METHOD_VALUE = "convert";
    private static final String PARAM_URL = "backOnUrl";
    private static final String PARAM_DATA = "requestData";
    private static final String PARAM_CODE = "code";
    private static final String DEFAULT_PARAM_CODE_VALUE = "code";
    private static final String DEFAULT_RESULT = "$.#root";
    private static final String UNDERSCORE = "_";
    private static final String START_NODE_CODE = "0000000000";
    private static final String END_NODE_CODE = "1111111111";
    private static final String PARAM_SUCCESS_VALUE = "success";


    public String generate(String json) {
        List list = new ArrayList();
        JSONObject jsonObject = removeStartAndEnd(json);
        GatewayInterface gatewayInterface = jsonObject.getObject("gatewayInterface", GatewayInterface.class);
        StateMachine stateMachine = new StateMachine();
        String gatewayInterfaceUrl = gatewayInterface.getUrl();
        stateMachine.setName(safeEncodeStateUrl(gatewayInterfaceUrl + UNDERSCORE + "state"));
        String name = gatewayInterface.getName();
        stateMachine.setComment(name + STATE_MACHINE_SUFFIX);
        stateMachine.setVersion(DEFAULT_STATE_MACHINE_VERSION);
        JSONArray nodes = jsonObject.getJSONArray("nodes");
        List<JSONObject> nodeList = nodes.toJavaList(JSONObject.class);
        JSONArray edges = jsonObject.getJSONArray("edges");
        LinkedList<String> buildList = buildList(edges);
        System.err.println(edges);

        for (int i = 0; i < buildList.size(); i++) {
            String id = buildList.get(i);
            JSONObject node = nodeList.stream().filter(n -> n.getString("id").equals(id)).findFirst().get();
            State state = new State();
            String stateName = null;
            //根据ID查询
            String stateType = node.getString("stateType");
            if (STATE_CONVERTER.equals(stateType)) {
                MessageConverter messageConverter = messageConverterMapper.selectOne(new QueryWrapper<MessageConverter>().eq("id", id).eq("status", 1).eq("is_deleted", "0"));
                String url = messageConverter.getUrl();
                stateName = url + UNDERSCORE + STATE_CONVERTER;
                list.add(messageConverter);
            } else if (STATE_TASK.equals(stateType)) {
                BackonInterface backonInterface = backonInterfaceMapper.selectOne(new QueryWrapper<BackonInterface>().eq("url", node.getString("url")).eq("status", 1).eq("is_deleted", "0"));
                String url = backonInterface.getUrl();
                stateName = url + UNDERSCORE + STATE_TASK;
                list.add(backonInterface);
            }
            stateName = safeEncodeStateUrl(stateName);
            state.setType(TASK_TYPE);
            if (i == 0) {//第一个
                stateMachine.setStartState(stateName);
            } else if (i == nodes.size() - 1) {//最后一个
                state.setNext(SUCCESS_STATE);
            }
            if (i > 0 && i <= nodes.size() - 1) {
                String lastUrlAndType = getLastUrlAndType(list.get(i - 1));
                String lastStateName = safeEncodeStateUrl(lastUrlAndType);
                State lastState = stateMachine.getStates().get(lastStateName);
                lastState.setNext(stateName);
            }
            Map<String, String> input = new HashMap<>();
            if (STATE_CONVERTER.equalsIgnoreCase(stateType)) {
                state.setServiceName(CONVERTER_TASK_NAME_VALUE);
                state.setServiceMethod(CONVERTER_TASK_METHOD_VALUE);
                String lastUrlAndType = getLastUrlAndType(list.get(i - 1));
                String lastStateResult = safeEncodeStateUrl(lastUrlAndType) + "_result";
                input.put("data", "$.[" + lastStateResult + "].data");
                input.put("context", "$.#root");
                input.put("current", stateName);
            } else if (STATE_TASK.equalsIgnoreCase(stateType)) {
                state.setServiceName(INVOKE_TASK_NAME_VALUE);
                state.setServiceMethod(INVOKE_TASK_METHOD_VALUE);
                if (i > 0) {
                    Object lastObject = list.get(i - 1);
                    if (lastObject instanceof MessageConverter) {
                        MessageConverter messageConverter = (MessageConverter) lastObject;
                        String lastTaskUrl = messageConverter.getUrl();
                        String lastStateResult = safeEncodeStateUrl(lastTaskUrl + UNDERSCORE + STATE_CONVERTER) + "_result";
                        input.put(PARAM_DATA, "$.[" + lastStateResult + "]");
                    } else if (lastObject instanceof BackonInterface) {
                        input.put(PARAM_DATA, "$.[" + stateName + "][requestData]");
                    }
                } else {
                    input.put(PARAM_DATA, "$.[" + stateName + "][requestData]");
                }

                input.put(PARAM_URL, "$.[" + stateName + "][backOnUrl]");

                BackonInterface backonInterface = (BackonInterface)list.get(i);
                String system = backonInterface.getSystem();
                Backon backon = backonMapper.selectOne(new QueryWrapper<Backon>().eq("system", system).eq("status", "1").eq("is_deleted", "0"));
                String successCode = backon.getSuccessCode();
                String successValue = backon.getSuccessValue();
                input.put(PARAM_CODE, "$.[" + stateName + "]["+successCode+"]");
                input.put(PARAM_SUCCESS_VALUE, "$.[" + stateName + "]["+successValue+"]");
            }
            List<Map<String, String>> inputs = new ArrayList<>();
            inputs.add(input);
            Map<String, String> output = new HashMap<>();
            output.put(stateName + "_result", DEFAULT_RESULT);
            state.setInput(inputs);
            state.setOutput(output);
            stateMachine.getStates().put(stateName, state);
        }
        State state = new State();
        state.setType(SUCCESS_STATE);
        stateMachine.getStates().put(SUCCESS_STATE, state);
        return JSONObject.toJSONString(stateMachine);
    }

    public static String safeEncodeStateUrl(String url) {
        return Base64.encodeBase64URLSafeString(url.getBytes());
    }

    public static String getUrlFromEncodeStateName(String stateName) {
        byte[] bytes = Base64.decodeBase64(stateName);
        String decode = new String(bytes);
        String url = null;
        if (decode.endsWith(UNDERSCORE + STATE_TASK)) {
            url = decode.substring(0, decode.length() - (UNDERSCORE + STATE_TASK).length());
        } else if (decode.endsWith(UNDERSCORE + STATE_CONVERTER)) {
            url = decode.substring(0, decode.length() - (UNDERSCORE + STATE_CONVERTER).length());
        } else {
            throw new GatewayException(ResponseEnum.PARSE_ENCODE_URL_ERROT);
        }
        return url;
    }

    private JSONObject removeStartAndEnd(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List nodes = jsonObject.getObject("nodes", List.class);
        Iterator iterator = nodes.iterator();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String id = next.getString("id");
            if (START_NODE_CODE.equals(id) || END_NODE_CODE.equals(id))
                iterator.remove();
        }
        return jsonObject;
    }

    private String getLastUrlAndType(Object lastObject) {
        String lastTaskUrl = null;
        String lastTaskType = null;
        if (lastObject instanceof MessageConverter) {
            MessageConverter messageConverter = (MessageConverter) lastObject;
            lastTaskUrl = messageConverter.getUrl();
            lastTaskType = STATE_CONVERTER;
        } else if (lastObject instanceof BackonInterface) {
            BackonInterface backonInterface = (BackonInterface) lastObject;
            lastTaskUrl = backonInterface.getUrl();
            lastTaskType = STATE_TASK;
        }
        return lastTaskUrl + UNDERSCORE + lastTaskType;
    }

    public static LinkedList<String> buildList(JSONArray edges) {
        LinkedList<String> nodeOrder = new LinkedList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (START_NODE_CODE.equals(edges.getJSONObject(i).getString("source")) || END_NODE_CODE.equals(edges.getJSONObject(i).getString("target"))) {
                continue;
            }
            JSONObject edge = edges.getJSONObject(i);
            String source = edge.getString("source");
            int index = nodeOrder.indexOf(source);
            if (index > 0) {
                String target = edge.getString("target");
                nodeOrder.add(index+1, target);
            } else {
                nodeOrder.addLast(source);
                String target = edge.getString("target");
                nodeOrder.addLast(target);
            }
        }
        return nodeOrder;
    }

}
