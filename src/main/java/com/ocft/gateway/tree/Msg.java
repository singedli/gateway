package com.ocft.gateway.tree;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:48
 * @Description:
 */
@Data
public class Msg implements Serializable {
    private Integer status=null;
    private String message=null;

}
