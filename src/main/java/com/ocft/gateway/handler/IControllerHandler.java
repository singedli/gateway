package com.ocft.gateway.handler;

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
	public Map<String, Object> handle(String body, HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}