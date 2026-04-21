package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Graphics;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
public interface IGraphicsService extends IService<Graphics> {

    long selectCount();

    String findLast();
}
