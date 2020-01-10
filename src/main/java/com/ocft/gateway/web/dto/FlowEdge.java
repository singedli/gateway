package com.ocft.gateway.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijiaxing
 * @Title: FlowEdge
 * @ProjectName gateway
 * @date 2019/12/26下午4:23
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class FlowEdge {
    private int sourceAnchor;
    private int targetAnchor;
    private int index;
    private String source;
    private String id;
    private String target;
}
