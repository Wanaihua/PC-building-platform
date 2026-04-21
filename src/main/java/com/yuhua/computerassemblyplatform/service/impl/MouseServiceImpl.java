package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.yuhua.computerassemblyplatform.entity.Mouse;
import com.yuhua.computerassemblyplatform.mapper.MouseMapper;
import com.yuhua.computerassemblyplatform.service.IMouseService;
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
public class MouseServiceImpl extends ServiceImpl<MouseMapper, Mouse> implements IMouseService {

    @Override
    public long selectCount() {
        QueryWrapper<Mouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Mouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getMouseImg();
    }
}
