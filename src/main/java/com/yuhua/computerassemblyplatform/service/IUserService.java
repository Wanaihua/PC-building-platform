package com.yuhua.computerassemblyplatform.service;

import com.yuhua.computerassemblyplatform.controller.dto.UserDTO;
import com.yuhua.computerassemblyplatform.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YuHua
 * @since 2022-10-21
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
