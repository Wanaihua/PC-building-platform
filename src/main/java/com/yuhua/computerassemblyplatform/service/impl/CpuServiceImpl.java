package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Cpu;
import com.yuhua.computerassemblyplatform.entity.Platform;
import com.yuhua.computerassemblyplatform.mapper.CpuMapper;
import com.yuhua.computerassemblyplatform.service.ICpuService;
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
public class CpuServiceImpl extends ServiceImpl<CpuMapper, Cpu> implements ICpuService {

    @Override
    public long selectCount() {
        QueryWrapper<Cpu> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Cpu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getCpuImg();
    }
}
