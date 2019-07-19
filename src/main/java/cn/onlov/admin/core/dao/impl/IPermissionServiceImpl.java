package cn.onlov.admin.core.dao.impl;

import cn.onlov.admin.core.dao.entities.OnlovPermission;
import cn.onlov.admin.core.dao.interfaces.IPermissionService;
import cn.onlov.admin.core.dao.mapper.PermissionMapper;
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
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, OnlovPermission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<OnlovPermission> loadUserPermissions(int id, int type,int systemId){
        List<OnlovPermission> list = permissionMapper.loadUserPermissions(id, type ,systemId);
        return list;
    }


    @Override
    public List<OnlovPermission> queryPermissionsListWithSelected(int rid ,int systemId){
        List<OnlovPermission> list = permissionMapper.queryPermissionsListWithSelected(rid,systemId);
        return list;
    }

}
