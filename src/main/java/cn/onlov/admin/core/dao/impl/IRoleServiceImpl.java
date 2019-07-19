package cn.onlov.admin.core.dao.impl;

import cn.onlov.admin.core.dao.entities.OnlovRole;
import cn.onlov.admin.core.dao.interfaces.IRoleService;
import cn.onlov.admin.core.dao.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kaifa
 * @since 2019-01-04
 */
@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, OnlovRole> implements IRoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<OnlovRole> queryRoleListByUserId(Integer roleId) {
        List<OnlovRole> onlovRoles = roleMapper.queryRoleListByUserId(roleId);

        return onlovRoles;
    }
}
