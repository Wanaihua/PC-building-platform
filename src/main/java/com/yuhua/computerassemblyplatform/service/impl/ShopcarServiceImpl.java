package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Shopcar;
import com.yuhua.computerassemblyplatform.mapper.ShopcarMapper;
import com.yuhua.computerassemblyplatform.service.IShopcarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-06
 */
@Service
public class ShopcarServiceImpl extends ServiceImpl<ShopcarMapper, Shopcar> implements IShopcarService {

    @Override
    public List<Shopcar> getByUserId(int userId) {
        QueryWrapper<Shopcar> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Shopcar> shopcar = baseMapper.selectList(queryWrapper);
        return shopcar;
    }
}
