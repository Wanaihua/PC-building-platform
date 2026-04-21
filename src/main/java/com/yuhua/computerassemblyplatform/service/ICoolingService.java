package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Cooling;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
public interface ICoolingService extends IService<Cooling> {

    long selectCount();

    String findLast();
}
