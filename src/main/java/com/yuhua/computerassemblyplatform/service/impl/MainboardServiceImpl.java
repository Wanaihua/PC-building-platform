package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.yuhua.computerassemblyplatform.entity.Mainboard;
import com.yuhua.computerassemblyplatform.mapper.MainboardMapper;
import com.yuhua.computerassemblyplatform.service.IMainboardService;
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
public class MainboardServiceImpl extends ServiceImpl<MainboardMapper, Mainboard> implements IMainboardService {

    @Override
    public long selectCount() {
        QueryWrapper<Mainboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*)");
        return 0;
    }

    @Override
    public String findLast() {
        QueryWrapper<Mainboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getMainboardImg();
    }
}
