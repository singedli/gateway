package com.ocft.gateway.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.InterfaceConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocft.gateway.web.dto.request.QueryInterfaceRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bobby
 * @since 2019-11-28
 */
public interface InterfaceConfigMapper extends BaseMapper<InterfaceConfig> {

    List<InterfaceConfig> queryInterfaceConfigs(Page<InterfaceConfig> page, @Param("req") QueryInterfaceRequest request);
}
