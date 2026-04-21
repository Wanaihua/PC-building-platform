package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.yuhua.computerassemblyplatform.entity.Keyboard;
import com.yuhua.computerassemblyplatform.mapper.KeyboardMapper;
import com.yuhua.computerassemblyplatform.service.IKeyboardService;
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
public class KeyboardServiceImpl extends ServiceImpl<KeyboardMapper, Keyboard> implements IKeyboardService {

    @Override
    public long selectCount() {
        QueryWrapper<Keyboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Keyboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getKeyboardImg();
    }
}
