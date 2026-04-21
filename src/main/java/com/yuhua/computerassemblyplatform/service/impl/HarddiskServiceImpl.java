package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.yuhua.computerassemblyplatform.mapper.HarddiskMapper;
import com.yuhua.computerassemblyplatform.service.IHarddiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
@Service
public class HarddiskServiceImpl extends ServiceImpl<HarddiskMapper, Harddisk> implements IHarddiskService {

    @Override
    public long selectCount() {
        QueryWrapper<Harddisk> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Harddisk> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getHarddiskImg();
    }
}
