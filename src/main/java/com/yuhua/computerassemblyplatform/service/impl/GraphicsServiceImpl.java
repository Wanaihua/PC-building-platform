package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Graphics;
import com.yuhua.computerassemblyplatform.entity.Platform;
import com.yuhua.computerassemblyplatform.mapper.GraphicsMapper;
import com.yuhua.computerassemblyplatform.service.IGraphicsService;
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
public class GraphicsServiceImpl extends ServiceImpl<GraphicsMapper, Graphics> implements IGraphicsService {
    @Override
    public long selectCount() {
        QueryWrapper<Graphics> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Graphics> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        Graphics graphics = baseMapper.selectOne(queryWrapper);
        return graphics.getGraphicsImg();
    }
}
