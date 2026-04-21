package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Shopcar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-06
 */
public interface IShopcarService extends IService<Shopcar> {

    List<Shopcar> getByUserId(int userId);
}
