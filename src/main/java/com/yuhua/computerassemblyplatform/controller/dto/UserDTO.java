package com.yuhua.computerassemblyplatform.controller.dto;

import com.yuhua.computerassemblyplatform.entity.Menu;
import lombok.Data;

import java.util.List;

/*
接受前端登录传来的参数
 */
@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String address;
    private String phone;
    private String token;
    private List<Menu> menus;
    private String role;

}
