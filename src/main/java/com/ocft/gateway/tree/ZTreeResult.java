package com.ocft.gateway.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/12/11 16:43
 * @Description:
 */
@Data
public class ZTreeResult implements Serializable {
    private Msg msg;
    private List<ZTreeVO> data;
}
