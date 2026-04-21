package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Memorysticks;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
public interface IMemorysticksService extends IService<Memorysticks> {

    long selectCount();

    String findLast();
}
