package com.ocft.gateway.tree;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:34
 * @Description:
 */
@Data
public class ZTreeVO implements Serializable {
    private Integer id;
    private Integer PId;
    private String name;
    private Integer seq;
    private boolean checked=false;
    private boolean open=true;

}
