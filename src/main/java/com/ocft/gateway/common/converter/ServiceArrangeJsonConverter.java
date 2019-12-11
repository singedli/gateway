package com.ocft.gateway.common.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.entity.GatewayInterface;

import java.util.*;

/**
 * @author lijiaxing
 * @Title: ServiceArrangeJsonConverter
 * @ProjectName gateway
 * @date 2019/12/10下午5:56
 * @Description: TODO
 */
public class ServiceArrangeJsonConverter {

    private static final String STATE_MACHINE_NAME = "Name";
    private static final String STATE_MACHINE_COMMENT = "Comment";
    private static final String STATE_MACHINE_START_STATE = "StartState";
    private static final String STATE_MACHINE_VERSION = "Version";
    private static final String STATE_MACHINE_VERSION_VALUE = "0.0.2";
    private static final String STATE_MACHINE_STATES = "States";
    private static final String STATE_MACHINE_TYPE = "Type";
    private static final String STATE_MACHINE_TYPE_VALUE = "Succeed";
    private static final String STATE_MACHINE_NEXT = "Next";
    private static final String STATE_MACHINE_INPUT = "Input";
    private static final String STATE_MACHINE_OUTPUT = "Output";

    private static final String TASK_TYPE = "ServiceTask";
    private static final String TASK_NAME = "ServiceName";
    private static final String TASK_METHOD = "ServiceMethod";

    private static final String INVOKE_TASK_NAME_VALUE = "defaultInvokeSao";
    private static final String INVOKE_TASK_METHOD_VALUE = "invokeHandler";
    private static final String CONVERTER_TASK_NAME_VALUE = "paramsConverter";
    private static final String CONVERTER_TASK_METHOD_VALUE = "convert";

    private static final String PARAM_URL = "backOnUrl";
    private static final String PARAM_DATA = "requestData";
    private static final String PARAM_CODE = "code";
    private static final String DEFAULT_PARAM_CODE_VALUE = "00000000";

    private static final String DEFAULT_RESULT = "$.#root";



    String json = "[{\"id\":\"111\",\"name\":\"获取文章列表\",\"system\":\"meisi\",\"url\":\"/top/weekTop\",\"status\":1,\"httpMethod\":\"POST\",\"version\":\"1.0.0\",\"isDeleted\":0,\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-11-28\",\"type\":\"调用接口任务\"},{\"id\":\"112\",\"name\":\"会议\",\"system\":\"meisi\",\"url\":\"/portal-medscimeeting/recommendMeeting\",\"status\":1,\"httpMethod\":\"POST\",\"version\":\"1.0.0\",\"isDeleted\":0,\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-11-28\",\"type\":\"调用接口任务\"},{\"id\":\"dd2d2a8127a97cc4c60a46f9eafa07e1\",\"name\":\"concurrentTest\",\"url\":\"/test/concurrentTest\",\"backonUrl\":\"/top/weekTop,/portal-medscimeeting/recommendMeeting\",\"requestConfig\":\"url=url123,page=page123,address.city=city123,links.name=name123\",\"responseConfig\":\"https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting=A,https://portal-test.medsci.cn/top/weekTop=B\",\"requestStruct\":\"{\\\"name\\\":\\\"BeJson\\\",\\\"url\\\":\\\"http://www.bejson.com\\\",\\\"page\\\":88,\\\"address\\\":{\\\"street\\\":\\\"科技园路.\\\",\\\"city\\\":\\\"江苏苏州\\\",\\\"country\\\":\\\"中国\\\"},\\\"links\\\":[{\\\"name\\\":\\\"Google\\\",\\\"url\\\":\\\"http://www.google.com\\\"},{\\\"name\\\":\\\"Baidu\\\",\\\"url\\\":\\\"http://www.baidu.com\\\"}]}\",\"responseStruct\":\"{\\\"https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting\\\":{\\\"data\\\":[{\\\"city\\\":\\\"上海\\\",\\\"name\\\":\\\"Mayo Clinic医疗模式国际研讨会\\\",\\\"startTime\\\":\\\"2019-08-13\\\",\\\"id\\\":3},{\\\"city\\\":\\\"上海\\\",\\\"name\\\":\\\"药物警戒培训班\\\",\\\"startTime\\\":\\\"2019-08-13\\\",\\\"id\\\":4}]},\\\"https://portal-test.medsci.cn/top/weekTop\\\":{\\\"data\\\":[{\\\"cover\\\":\\\"http://news.medlive.cn/uploadfile/ueditor/php/upload/image/20191028/1572252792613401.jpeg\\\",\\\"module\\\":\\\"article\\\",\\\"moduleId\\\":178255,\\\"title\\\":\\\"9位医生含泪说出的压箱底的救赎故事……\\\"},{\\\"cover\\\":\\\"http://refactor.test.medsci.cn/20191015/b7f3bf271e904c9f96374283dc5f50ab/20191014151336179.png\\\",\\\"module\\\":\\\"article\\\",\\\"moduleId\\\":178241,\\\"title\\\":\\\"原发性肺肉瘤样癌的CT表现与鉴别诊断\\\"}]}}\",\"status\":true,\"isDeleted\":false,\"createTime\":\"2019-12-08\",\"updateTime\":\"2019-12-08\",\"type\":\"报文转换器\"}]";
    String jiekou = "{\"id\":\"444\",\"name\":\"并发测试接口\",\"url\":\"/test/concurrentTest\",\"backonUrl\":\"/top/weekTop,/portal-medscimeeting/recommendMeeting\",\"type\":\"CONCURRENT\",\"status\":true,\"system\":\"订单中台\",\"preInterceptors\":\"messageConverterInterceptor\",\"postInterceptors\":\"messageConverterInterceptor\",\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-12-06\"}";

    public static void main(String[] args) {
        String json = "[{\"id\":\"111\",\"name\":\"获取文章列表\",\"system\":\"meisi\",\"url\":\"/top/weekTop\",\"status\":1,\"httpMethod\":\"POST\",\"version\":\"1.0.0\",\"isDeleted\":0,\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-11-28\",\"type\":\"task\"},{\"id\":\"dd2d2a8127a97cc4c60a46f9eafa07e1\",\"name\":\"concurrentTest\",\"url\":\"/test/concurrentTest\",\"backonUrl\":\"/top/weekTop,/portal-medscimeeting/recommendMeeting\",\"requestConfig\":\"url=url123,page=page123,address.city=city123,links.name=name123\",\"responseConfig\":\"https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting=A,https://portal-test.medsci.cn/top/weekTop=B\",\"requestStruct\":\"{\\\"name\\\":\\\"BeJson\\\",\\\"url\\\":\\\"http://www.bejson.com\\\",\\\"page\\\":88,\\\"address\\\":{\\\"street\\\":\\\"科技园路.\\\",\\\"city\\\":\\\"江苏苏州\\\",\\\"country\\\":\\\"中国\\\"},\\\"links\\\":[{\\\"name\\\":\\\"Google\\\",\\\"url\\\":\\\"http://www.google.com\\\"},{\\\"name\\\":\\\"Baidu\\\",\\\"url\\\":\\\"http://www.baidu.com\\\"}]}\",\"responseStruct\":\"{\\\"https://portal-test.medsci.cn/portal-medscimeeting/recommendMeeting\\\":{\\\"data\\\":[{\\\"city\\\":\\\"上海\\\",\\\"name\\\":\\\"Mayo Clinic医疗模式国际研讨会\\\",\\\"startTime\\\":\\\"2019-08-13\\\",\\\"id\\\":3},{\\\"city\\\":\\\"上海\\\",\\\"name\\\":\\\"药物警戒培训班\\\",\\\"startTime\\\":\\\"2019-08-13\\\",\\\"id\\\":4}]},\\\"https://portal-test.medsci.cn/top/weekTop\\\":{\\\"data\\\":[{\\\"cover\\\":\\\"http://news.medlive.cn/uploadfile/ueditor/php/upload/image/20191028/1572252792613401.jpeg\\\",\\\"module\\\":\\\"article\\\",\\\"moduleId\\\":178255,\\\"title\\\":\\\"9位医生含泪说出的压箱底的救赎故事……\\\"},{\\\"cover\\\":\\\"http://refactor.test.medsci.cn/20191015/b7f3bf271e904c9f96374283dc5f50ab/20191014151336179.png\\\",\\\"module\\\":\\\"article\\\",\\\"moduleId\\\":178241,\\\"title\\\":\\\"原发性肺肉瘤样癌的CT表现与鉴别诊断\\\"}]}}\",\"status\":true,\"isDeleted\":false,\"createTime\":\"2019-12-08\",\"updateTime\":\"2019-12-08\",\"type\":\"converter\"},{\"id\":\"112\",\"name\":\"会议\",\"system\":\"meisi\",\"url\":\"/portal-medscimeeting/recommendMeeting\",\"status\":1,\"httpMethod\":\"POST\",\"version\":\"1.0.0\",\"isDeleted\":0,\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-11-28\",\"type\":\"task\"}]";
        String jiekou = "{\"id\":\"444\",\"name\":\"并发测试接口\",\"url\":\"/test/concurrentTest\",\"backonUrl\":\"/top/weekTop,/portal-medscimeeting/recommendMeeting\",\"type\":\"CONCURRENT\",\"status\":true,\"system\":\"订单中台\",\"preInterceptors\":\"messageConverterInterceptor\",\"postInterceptors\":\"messageConverterInterceptor\",\"createTime\":\"2019-11-28\",\"updateTime\":\"2019-12-06\"}";
        String converter = converter(json, jiekou);
        System.out.println(converter);
    }

    /**
     * 状态名字中不能使用-，会有bug
     *
     * @param stateArray
     * @param data
     * @return
     */
    public static String converter(String stateArray, String data) {
        GatewayInterface gatewayInterface = JSONObject.parseObject(data, GatewayInterface.class);
        Map<String, Object> result = new HashMap<>();
        result.put(STATE_MACHINE_NAME, gatewayInterface.getUrl().replaceAll("\\/", "_") + "_state");
        result.put(STATE_MACHINE_COMMENT, gatewayInterface.getName() + "的状态机");
        result.put(STATE_MACHINE_START_STATE, "");
        result.put(STATE_MACHINE_VERSION, STATE_MACHINE_VERSION_VALUE);
        result.put(STATE_MACHINE_STATES, new HashMap());

        Map<String, Object> Succeed = new HashMap<>();
        Succeed.put(STATE_MACHINE_TYPE, STATE_MACHINE_TYPE_VALUE);


        JSONArray jsonArray = JSONArray.parseArray(stateArray);
        for (int i = 0; i < jsonArray.size(); i++) {
            Map<String, Object> state = new HashMap<>();
            JSONObject task = jsonArray.getJSONObject(i);
            //状态机的name
            String stateName = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(task.getString("url").getBytes())+ "_state";
            if (i == 0) {
                //设置初始任务为数组中的第一个任务
                result.put(STATE_MACHINE_START_STATE, stateName);
            } else if (i == jsonArray.size() - 1) {//如果是数组中的最后一个元素则指定next为Success
                state.put(STATE_MACHINE_NEXT, STATE_MACHINE_TYPE_VALUE);
            }

            if (i > 0 && i <= jsonArray.size() - 1) {
                JSONObject lastTask = jsonArray.getJSONObject(i - 1);
                String lastTaskUrl = lastTask.getString("url");
                String lastStateName = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(lastTaskUrl.getBytes()).toString()+ "_state";
                Map states = (Map) result.get(STATE_MACHINE_STATES);
                Map lastState = (Map) states.get(lastStateName);
                lastState.put(STATE_MACHINE_NEXT, stateName);
            }
            state.put(STATE_MACHINE_TYPE, TASK_TYPE);
            Map<String, String> input = new HashMap<>();
            if ("converter".equalsIgnoreCase(task.getString("type"))) {
                state.put(TASK_NAME, CONVERTER_TASK_NAME_VALUE);
                state.put(TASK_METHOD, CONVERTER_TASK_METHOD_VALUE);
                JSONObject lastTask = jsonArray.getJSONObject(i - 1);
                String lastTaskUrl = lastTask.getString("url");
                String lastStateResult = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(lastTaskUrl.getBytes())+ "_stateResult";
                input.put("data","$.["+lastStateResult+"].data");

            } else {
                state.put(TASK_NAME, INVOKE_TASK_NAME_VALUE);
                state.put(TASK_METHOD, INVOKE_TASK_METHOD_VALUE);
                if(i>0){
                    JSONObject lastTask = jsonArray.getJSONObject(i - 1);
                    String lastTaskType = lastTask.getString("type");
                    if("converter".equalsIgnoreCase(lastTaskType)){
                        String lastTaskUrl = lastTask.getString("url");
                        String lastStateResult = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(lastTaskUrl.getBytes())+ "_stateResult";
                        input.put(PARAM_DATA, "$.[" + lastStateResult + "][requestData]");
                    }else{
                        input.put(PARAM_DATA, "$.[" + stateName + "][requestData]");
                    }
                }else{
                    input.put(PARAM_DATA, "$.[" + stateName + "][requestData]");
                }
                input.put(PARAM_URL, "$.[" + stateName + "][backOnUrl]");
                input.put(PARAM_CODE, DEFAULT_PARAM_CODE_VALUE);
            }
            List<Object> inputs = new ArrayList<>();
            inputs.add(input);
            Map<String, String> output = new HashMap<>();
            output.put(stateName + "Result", DEFAULT_RESULT);
            state.put(STATE_MACHINE_INPUT, inputs);
            state.put(STATE_MACHINE_OUTPUT, output);
            Map states = (Map) result.get(STATE_MACHINE_STATES);
            states.put(stateName, state);
        }
        Map states = (Map) result.get(STATE_MACHINE_STATES);
        states.put(STATE_MACHINE_TYPE_VALUE, Succeed);

        return JSONObject.toJSONString(result);
    }

}
