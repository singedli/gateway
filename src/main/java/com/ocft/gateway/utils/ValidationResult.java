package com.ocft.gateway.utils;

import lombok.Data;

import java.util.Map;

@Data
public class ValidationResult {
    /**
	 *校验结果是否有错
	 */
    private boolean hasErrors;
	/**
     *校验错误信息
	 */
    private Map<String,String> errorMsg;

    public boolean isHasErrors() {
        return hasErrors;
    }
}