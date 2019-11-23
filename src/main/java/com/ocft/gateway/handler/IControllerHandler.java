package com.ocft.gateway.handler;

import com.ocft.gateway.entity.GatewayInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface IControllerHandler {

	/**
	 *	模板方法
	 *
	 * @param body 参数
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> handle(String body, HttpServletRequest request, HttpServletResponse response,GatewayInterface gatewayInterface)
			throws Exception;
}