package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.entity.Harddisk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
public interface IHarddiskService extends IService<Harddisk> {

    long selectCount();

    String findLast();
}
