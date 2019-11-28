package com.ocft.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocft.gateway.entity.RequestType;
import com.ocft.gateway.mapper.RequestTypeMapper;
import com.ocft.gateway.service.IRequestTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Bobby
 * @since 2019-11-26
 */
@Service
public class RequestTypeServiceImpl extends ServiceImpl<RequestTypeMapper, RequestType> implements IRequestTypeService {

    /**
     * 查询客户端的请求头
     *
     * @return
     */
    @Override
    public List<RequestType> findTypeBrowser() {
        List<RequestType> requestTypes = this.list(new QueryWrapper<RequestType>().eq("type", "browser"));
        return requestTypes;
    }


    /**
     * 查询app端请求头
     */
    @Override
    public List<RequestType> findTypeApp() {
        List<RequestType> requestTypes = this.list(new QueryWrapper<RequestType>().eq("type", "app"));
        return requestTypes;
    }


}
