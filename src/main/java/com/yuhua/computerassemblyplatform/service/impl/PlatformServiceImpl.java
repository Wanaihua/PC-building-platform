package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Platform;
import com.yuhua.computerassemblyplatform.mapper.PlatformMapper;
import com.yuhua.computerassemblyplatform.service.IPlatformService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements IPlatformService {

    @Override
    public long selectCount() {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getPlatformImg();
    }
}
