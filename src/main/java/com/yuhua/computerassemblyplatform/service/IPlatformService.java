package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Platform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
public interface IPlatformService extends IService<Platform> {

    long selectCount();

    String findLast();
}
