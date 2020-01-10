package com.ocft.gateway.web.dto.request;

import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.web.dto.FlowEdge;
import com.ocft.gateway.web.dto.FlowNode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lijiaxing
 * @Title: FlowStateLangRequest
 * @ProjectName gateway
 * @date 2019/12/26下午4:21
 * @Description: TODO
 */
@Data
@Accessors(chain = true)
public class FlowStateLangRequest {
    private GatewayInterface gatewayInterface;
    private List<FlowNode> nodes;
    private List<FlowEdge> edges;
}
