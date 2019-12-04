package com.ocft.gateway.interceptor.pre;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.evaluator.JsonOperateEvalutor;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.entity.InterfaceConfig;
import com.ocft.gateway.entity.RequestAccessLimit;
import com.ocft.gateway.entity.RequestType;
import com.ocft.gateway.interceptor.GatewayInterceptor;
import com.ocft.gateway.service.IInterfaceConfigService;
import com.ocft.gateway.service.IRequestTypeService;
import com.ocft.gateway.utils.MathUtil;
import com.ocft.gateway.utils.RedisUtil;
import com.ocft.gateway.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author lijiaxing
 * @Title: RequestLimitIntercept
 * @ProjectName gateway
 * @date 2019/11/23下午5:50
 * @Description: 接口防刷拦截器
 */

//todo 1恶意调用策率包2针对不同的请求会有不同的key做限制判断


@Slf4j
@Component
public class RequestLimitIntercept implements GatewayInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLimitIntercept.class);

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IRequestTypeService irequestTypeService;

    @Autowired
    IInterfaceConfigService iInterfaceConfigService;


    @Override
    public void doInterceptor(GatewayContext context) {
        //是否允许登陆的标志
        boolean accessFlag = true;

        //当前时间
        long currentTime = new Date().getTime();
        //获取请求参数
        GatewayInterface gatewayInterface = context.getGatewayInterface();
        String requestBody = context.getRequestBody();
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        //获取接口对应的限制策率数据
        InterfaceConfig interfaceConfig = iInterfaceConfigService.getByUrl(gatewayInterface.getUrl());


        //获取ip 或者设备号
        String ipOrdeviceStr = "";
        String type = checkAgentIsBrowser(context.getRequest());
        if (StringUtils.isNoneBlank(type) && type.equals("0")) {
            ipOrdeviceStr = WebUtil.getRealIp(context.getRequest());
        } else if (StringUtils.isNoneBlank(type) && type.equals("1")) {
            ipOrdeviceStr = WebUtil.getMobileDevice(context.getRequest());
        } else {
            logger.error("无法确定数据来源");
            throw new GatewayException("500", "服务异常，请求来源无法鉴定");
        }
        //判断redis中的ip数据和设备号数据
        String strReqAccessLimit = redisUtil.get(ipOrdeviceStr);
        if (StringUtils.isNotBlank(strReqAccessLimit)) {
            //有数据的话就根据当前数据做判断
            RequestAccessLimit accessLimit = JSONObject.parseObject(strReqAccessLimit, RequestAccessLimit.class);
            Boolean needLogin = accessLimit.getNeedLogin();
            if (needLogin) {
                //可以登录 判断单位内请求次数
                long timeFrame = currentTime - accessLimit.getFirstRequestTime();
                long totalCount = accessLimit.getCount() + 1;
                accessLimit.setCount(totalCount);

                boolean checkResult = checkMaxCout(totalCount, timeFrame, interfaceConfig);
                if (!checkResult) {
                    logger.error("请求频率超过限制限制");
                    accessFlag = checkResult;
                    accessLimit.setNeedLogin(checkResult);
                }
                String keyLimitFlag = getByKey(interfaceConfig, jsonObject);
                if (StringUtils.isNotBlank(keyLimitFlag) && keyLimitFlag.equals("1")) {
                    throw new GatewayException("500", "服务异常，请求限制");
                }
                //超过次数 redis中的数据做needLogin修改为false 过期时间为一周
                //最后更新redis中的数据
                String strAccessLimit = JSONObject.toJSONString(accessLimit);
                redisUtil.set(ipOrdeviceStr, strAccessLimit, 604800);
                if (!accessFlag) {
                    //修改对应的key参数值
                    setByKey(interfaceConfig, jsonObject, "1");
                    throw new GatewayException("500", "服务异常，请求限制");
                }
            } else {
                throw new GatewayException("500", "服务异常，请求限制");
            }
        } else {
            //没有数据的话就添加数据
            RequestAccessLimit accessLimit = new RequestAccessLimit();
            accessLimit.setFirstRequestTime(currentTime);
            accessLimit.setNeedLogin(true);
            accessLimit.setCount(1);
            String strAccessLimit = JSONObject.toJSONString(accessLimit);
            redisUtil.set(ipOrdeviceStr, strAccessLimit, 604800);
            setByKey(interfaceConfig, jsonObject, "0");
        }
    }


    /**
     * 判redis中的指定的key数据
     */
    private String getByKey(InterfaceConfig interfaceConfig, JSONObject requestBody) {
        String flag = "0";
        String keyLimit = interfaceConfig.getKeyLimit();
        if (StringUtils.isNotBlank(keyLimit)) {
            String[] keys = keyLimit.split(",");
            for (int i = 0; i < keys.length; i++) {
                //取得request中对应的请求数据
                Object jsonPropertyValue = JsonOperateEvalutor.getJsonPropertyValue(requestBody, keys[i]);
                String value = jsonPropertyValue + "";
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                //判断redis 对应value是否被限制
                String limitBykey = redisUtil.get(value);
                if (StringUtils.isBlank(limitBykey)) {
                    continue;
                }
                flag = limitBykey;
                if (limitBykey.equals("1")) {
                    logger.error(keys[i] + "为" + value + "的被限制请求");
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 修改redis中指定的key数据
     *
     * @param gatewayInterface
     * @param requestBody
     * @param flag             0为不限制 1为限制登陆
     */
    private void setByKey(InterfaceConfig interfaceConfig, JSONObject requestBody, String flag) {
        String keyLimit = interfaceConfig.getKeyLimit();
        if (StringUtils.isNotBlank(keyLimit)) {
            String[] keys = keyLimit.split(",");
            for (int i = 0; i < keys.length; i++) {
                //取得request中对应的请求数据
                Object jsonPropertyValue = JsonOperateEvalutor.getJsonPropertyValue(requestBody, keys[i]);
                String value = jsonPropertyValue + "";
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                //有就设置对应的flag
                redisUtil.set(value, flag, 604800);
            }
        }
    }


    /**
     * 验证请求来源
     *
     * @param request
     * @return
     */
    private String checkAgentIsBrowser(HttpServletRequest request) {
        String flag = "";
        List<RequestType> typeBrowsers = irequestTypeService.findTypeBrowser();//客户端
        List<RequestType> typeApps = irequestTypeService.findTypeApp();//app端
        String userAgent = request.getHeader("user-agent");
        logger.info("user-agent :" + userAgent);
        for (RequestType type : typeBrowsers) {
            if (userAgent.contains(type.getAgent())) {
                flag = "0";
                break;
            }
        }
        if (StringUtils.isNotBlank(flag)) {
            return flag;
        }
        for (RequestType type : typeApps) {
            if (userAgent.contains(type.getAgent())) {
                flag = "1";
                break;
            }
        }
        return flag;
    }

    //校验单位时间是否超过最大次数
    private boolean checkMaxCout(long totalCount, long timeFrame, InterfaceConfig interfaceConfig) {
        Double totalCountD = Double.valueOf(totalCount);
        Double timeFrameD = Double.valueOf(timeFrame);
        Double maxCount = interfaceConfig.getMaxCount();
        String timeUnit = interfaceConfig.getTimeUnit();
        Double halfup;
        if (timeUnit.equals("时")) {
            halfup = MathUtil.halfup(totalCountD / (timeFrameD / 60.00 / 60.00), 2);

        } else if (timeUnit.equals("分")) {
            halfup = MathUtil.halfup(totalCountD / (timeFrameD / 60.00), 2);
        } else {
            halfup = MathUtil.halfup(totalCountD / timeFrameD, 2);//默认为秒
        }
        if (halfup > maxCount) {
            return false;
        }
        return true;
    }
}
