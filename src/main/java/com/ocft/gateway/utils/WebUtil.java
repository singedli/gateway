package com.ocft.gateway.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Bobby
 * @create: 2019-11-26 09:58
 * @description: 从请求体中获取数据的工具
 **/
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * 获取真实IP
     *
     * @param request 请求体
     * @return 真实IP
     */
    public static String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        try {
            String ip = request.getHeader("X-Real-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            // 处理多IP的情况（只取第一个IP）
            if (ip != null && ip.contains(",")) {
                String[] ipArray = ip.split(",");
                ip = ipArray[0];
            }
            return ip;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("WebUtil 获取ip出错");
            return null;
        }
    }


    /**
     * 获取设备号
     */
    public static String getMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        String deviceStr = "";
        try {
            if (StringUtils.isNotEmpty(userAgent)) {
                int startIndex = userAgent.indexOf("(");
                int endIndex = userAgent.indexOf(")");
                deviceStr = userAgent.substring(startIndex + 1, endIndex);
                return deviceStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WebUtil 获取设备号出错");
        }
        return null;
    }
}
