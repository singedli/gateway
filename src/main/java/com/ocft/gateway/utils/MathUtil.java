package com.ocft.gateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author: Bobby
 * @create: 2019-12-04 10:40
 * @description: 数字工具
 **/
public class MathUtil {

    private static final Logger logger = LoggerFactory.getLogger(MathUtil.class);


    public static double halfup(double d, int capacity) {
        BigDecimal b = new BigDecimal(d);
        double newDouble = b.setScale(capacity, BigDecimal.ROUND_HALF_UP).doubleValue();
        return newDouble;
    }
}
