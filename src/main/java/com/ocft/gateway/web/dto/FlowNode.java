package com.ocft.gateway.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijiaxing
 * @Title: FlowNode
 * @ProjectName gateway
 * @date 2019/12/26下午4:23
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class FlowNode {
    private String type;
    private String size;
    private String shape;
    private String color;
    private String label;
    private String x;
    private String y;
    private String id;
    private String index;
    private String stateType;
    private String system;
    private String url;
}
