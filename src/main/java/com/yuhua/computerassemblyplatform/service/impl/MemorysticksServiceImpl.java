package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Memorysticks;
import com.yuhua.computerassemblyplatform.mapper.MemorysticksMapper;
import com.yuhua.computerassemblyplatform.service.IMemorysticksService;
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
public class MemorysticksServiceImpl extends ServiceImpl<MemorysticksMapper, Memorysticks> implements IMemorysticksService {

    @Override
    public long selectCount() {
        QueryWrapper<Memorysticks> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Memorysticks> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getMemorySticksImg();
    }
}
