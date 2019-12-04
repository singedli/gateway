package com.ocft.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ocft.gateway.entity.GatewayCache;
import com.ocft.gateway.web.dto.request.QueryGatewayCacheRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Auther: 梵高先生
 * @Date: 2019/11/27 14:16
 * @Description:
 */
@Mapper
public interface GatewayCacheMapper extends BaseMapper<GatewayCache> {

    List<GatewayCache> getGatewayCacheList(@Param("request") QueryGatewayCacheRequest request);
}
