package com.ocft.gateway.stateMachineBak;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 梵高先生
 * @Date: 2020/1/7 15:32
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ServiceTask extends State {

    @JSONField(name = "ServiceName")
    private String serviceName;//服务名

    @JSONField(name = "ServiceMethod")
    private String serviceMethod;//服务方法名

    @JSONField(name = "CompensateState")
    private String compensateState;//补偿状态

    @JSONField(name = "IsForUpdate")
    private Boolean isForUpdate = false;//是否更新数据

    @JSONField(name = "IsPersist")
    private Boolean isPersist = true;//是否进行存储

    @JSONField(name = "IsAsync")
    private Boolean isAsync = false;//异步调用服务

    @JSONField(name = "ParameterTypes")
    private List<String> parameterTypes;//参数类型

    @JSONField(name = "Input")
    private List<Object> input;//输入参数列表

    @JSONField(name = "Output")
    private Map<String,Object> output;//服务返回的参数

    @JSONField(name = "Status")
    private Map<String,Object> status;//服务执行状态映射

    @JSONField(name = "Catch")
    private List<Map<String,Object>> catchs;//捕获到异常后的路由

    @JSONField(name = "Retry")
    private List<Map<String,Object>> retry;//捕获异常后的重试策略

    @JSONField(name = "Next")
    private String next;//下一个执行的"状态"
}
