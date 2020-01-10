package com.ocft.gateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.web.dto.FlowNode;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.Charset;

import static com.ocft.gateway.common.BizConstantPool.*;

/**
 * @author lijiaxing
 * @Title: StateMachineUtil
 * @ProjectName gateway
 * @date 2019/12/26下午4:31
 * @Description: TODO
 */
public class StateMachineUtil {

    public static String safeEncode(String string) {
        return Base64.encodeBase64URLSafeString(string.getBytes());
    }

    public static String safeDecode(String encoded){
        byte[] bytes = Base64.decodeBase64(encoded);
        return new String(bytes,Charset.forName("UTF-8"));
    }

    public static String createStateName(FlowNode flowNode) {
        String stateType = flowNode.getStateType();
        String stateName = "";
        String url = flowNode.getUrl();
        if (STATE_TYPE_TASK.equalsIgnoreCase(stateType)) {
            stateName = url + STATE_DEFAULT_SEPARATOR + STATE_TYPE_TASK;
        } else if (STATE_TYPE_CONVERTER.equalsIgnoreCase(stateType)) {
            stateName = url + STATE_DEFAULT_SEPARATOR + STATE_TYPE_CONVERTER;
        }else {
            stateName = url + STATE_DEFAULT_SEPARATOR + STATE_TYPE_TASK;
        }
        return stateName;
    }

    public static String getEncodedStateName(FlowNode flowNode){
        return safeEncode(createStateName(flowNode));
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "            \"color\": \"#FA8C16\",\n" +
                "            \"id\": \"f08db3c479ccabfa400c2eaf0f47437f\",\n" +
                "            \"index\": \"1\",\n" +
                "            \"label\": \"调用medsci系统的\\n/top/weekTop接口\",\n" +
                "            \"shape\": \"flow-rect\",\n" +
                "            \"size\": \"200*80\",\n" +
                "            \"stateType\": \"task\",\n" +
                "            \"system\": \"medsci\",\n" +
                "            \"type\": \"node\",\n" +
                "            \"url\": \"/top/weekTop\",\n" +
                "            \"x\": \"648\",\n" +
                "            \"y\": \"174\"\n" +
                "        }";
        FlowNode flowNode = JSONObject.parseObject(json, FlowNode.class);
        String encodedStateName = getEncodedStateName(flowNode);
        System.out.println(encodedStateName);
        System.out.println(safeDecode(encodedStateName));
    }
}
