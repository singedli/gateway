package com.ocft.gateway.handler;

import com.ocft.gateway.common.context.GatewayContext;

import java.util.Map;

public interface IControllerHandler {

	/**
	 *  模板方法
	 * @param gatewayContext
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> handle(GatewayContext gatewayContext)
			throws Exception;
}