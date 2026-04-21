package com.yuhua.computerassemblyplatform.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yuhua.computerassemblyplatform.entity.Menu;
import com.yuhua.computerassemblyplatform.entity.Role;
import com.yuhua.computerassemblyplatform.entity.RoleMenu;
import com.yuhua.computerassemblyplatform.mapper.RoleMapper;
import com.yuhua.computerassemblyplatform.mapper.RoleMenuMapper;
import com.yuhua.computerassemblyplatform.service.IMenuService;
import com.yuhua.computerassemblyplatform.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuHua
 * @since 2022-11-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        /*QueryWrapper<RoleMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);*/

        //删除角色对应的菜单
        roleMenuMapper.deleteByRoleId(roleId);

        //添加角色对应的菜单
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if(menu.getPid()!=null&&!menuIdsCopy.contains(menu.getPid())){ //表示二级菜单并且传过来的menuId数组里面没有它的父级id
                //那么我们就补上父级id
                RoleMenu roleMenu=new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());

            }
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);



        }


    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
