package com.ocft.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.GatewayInterface;
import com.ocft.gateway.web.dto.request.QueryGatewayInterfaceRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lijiaxing
 * @since 2019-11-22
 */
public interface GatewayInterfaceMapper extends BaseMapper<GatewayInterface> {
    List<GatewayInterface> queryGateWayInterfaces(Page<GatewayInterface> page, @Param("req") QueryGatewayInterfaceRequest request);
}
