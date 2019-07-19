package cn.onlov.admin.core.dao.impl;

import cn.onlov.admin.core.dao.entities.OnlovUserRole;
import cn.onlov.admin.core.dao.interfaces.IUserRoleService;
import cn.onlov.admin.core.dao.mapper.CycleUserRoleMapper;
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
public class IUserRoleServiceImpl extends ServiceImpl<CycleUserRoleMapper, OnlovUserRole> implements IUserRoleService {

}
