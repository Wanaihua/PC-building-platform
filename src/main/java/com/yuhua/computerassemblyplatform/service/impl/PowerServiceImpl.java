package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.yuhua.computerassemblyplatform.entity.Power;
import com.yuhua.computerassemblyplatform.mapper.PowerMapper;
import com.yuhua.computerassemblyplatform.service.IPowerService;
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
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements IPowerService {

    @Override
    public long selectCount() {
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getPowerImg();
    }
}
