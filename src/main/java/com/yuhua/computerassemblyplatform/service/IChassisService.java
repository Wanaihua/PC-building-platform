package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Chassis;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
public interface IChassisService extends IService<Chassis> {

    long selectCount();

    String findLast();
}
