package com.ocft.gateway.web.vo;

import lombok.Data;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/2 15:32
 * @Description:
 */
@Data
public class ResultVO<T> {

    /* 错误码 */
    private Integer code;

    /* 提示信息 */
    private String msg;

    /* 具体内容 */
    private T data;
}
