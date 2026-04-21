package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Power;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
public interface IPowerService extends IService<Power> {

    long selectCount();

    String findLast();
}
