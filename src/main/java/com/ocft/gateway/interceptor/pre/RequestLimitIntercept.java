package com.ocft.gateway.interceptor.pre;

import com.alibaba.fastjson.JSONObject;
import com.ocft.gateway.common.context.GatewayContext;
import com.ocft.gateway.common.exceptions.GatewayException;
import com.ocft.gateway.entity.RequestAccessLimit;
import com.ocft.gateway.entity.RequestType;
import com.ocft.gateway.interceptor.GatewayInterceptor;
import com.ocft.gateway.service.IRequestTypeService;
import com.ocft.gateway.utils.RedisUtil;
import com.ocft.gateway.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

//todo 1恶意调用策率包  2用户账号密码  计算器


@Slf4j
@Component
public class RequestLimitIntercept implements GatewayInterceptor {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IRequestTypeService irequestTypeService;


    @Override
    public void doInterceptor(GatewayContext context) {
        long currentTime = new Date().getTime();
        //username获取
        String requestBody = context.getRequestBody();
        JSONObject jsonObject = JSONObject.parseObject(requestBody);
        String username = jsonObject.get("username") + "";
        //获取ip 或者设备号
        String ipOrdeviceStr = "";
        String type = checkAgentIsBrowser(context.getRequest());
        if (StringUtils.isNoneBlank(type) && type.equals("0")) {
            ipOrdeviceStr = WebUtil.getRealIp(context.getRequest());
        } else if (StringUtils.isNoneBlank(type) && type.equals("1")) {
            ipOrdeviceStr = WebUtil.getMobileDevice(context.getRequest());
        } else {
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
                //默认接口请求频率限制：48次/秒
                if (totalCount / timeFrame > 48) {
                    //超过48次 redis中的数据做needLogin修改为false 过期时间为一周
                    accessLimit.setNeedLogin(false);
                    String s = JSONObject.toJSONString(accessLimit);
                    redisUtil.set(ipOrdeviceStr, s, 604800);
                    redisUtil.set(username, "1", 604800);
                    throw new GatewayException("500", "服务异常，请求限制");
                }
                String byUsernama = getByUsernama(username);
                if (StringUtils.isNotBlank(byUsernama) && byUsernama.equals("1")) {
                    throw new GatewayException("500", "服务异常，请求限制");
                }
            } else {
                throw new GatewayException("500", "请求限制");
            }
        } else {
            //没有数据的话就添加数据  ip以及username都需要添加
            RequestAccessLimit accessLimit = new RequestAccessLimit();
            accessLimit.setFirstRequestTime(currentTime);
            accessLimit.setNeedLogin(true);
            accessLimit.setCount(1);
            accessLimit.setPassword(jsonObject.get("password") + "");
            accessLimit.setUsername(username);
            String s = JSONObject.toJSONString(accessLimit);
            redisUtil.set(ipOrdeviceStr, s, 604800);
            redisUtil.set(username, "0", 604800);
        }
    }

    /**
     * 判redis中的username数据
     */
    private String getByUsernama(String username) {
        String limitByUsername = redisUtil.get(username);
        return limitByUsername;
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
        for (RequestType type : typeBrowsers) {
            if (userAgent.contains(type.getAgent())) {
                flag = "0";
                break;
            }
        }
        if (StringUtils.isNoneBlank(flag)) {
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
}
