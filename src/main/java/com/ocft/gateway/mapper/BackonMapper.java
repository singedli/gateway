package com.ocft.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ocft.gateway.entity.Backon;
import com.ocft.gateway.web.dto.request.BackonRequest;
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
public interface BackonMapper extends BaseMapper<Backon> {

    List<Backon> queryBackons(Page<Backon> page,  @Param("req")BackonRequest req);

    void deleteByIds(@Param("ids")List<String> ids);
}
