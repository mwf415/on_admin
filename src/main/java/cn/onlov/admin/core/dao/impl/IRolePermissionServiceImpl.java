package cn.onlov.admin.core.dao.impl;

import cn.onlov.admin.core.dao.entities.OnlovRolePermission;
import cn.onlov.admin.core.dao.interfaces.IRolePermissionService;
import cn.onlov.admin.core.dao.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kaifa
 * @since 2019-01-04
 */
@Service
public class IRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, OnlovRolePermission> implements IRolePermissionService {

}
