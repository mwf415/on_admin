package cn.onlov.admin.service.impl;

import cn.onlov.admin.constants.Constants;
import cn.onlov.admin.core.dao.entities.OnlovPermission;
import cn.onlov.admin.core.dao.entities.OnlovRolePermission;
import cn.onlov.admin.core.dao.interfaces.IPermissionService;
import cn.onlov.admin.core.dao.interfaces.IRolePermissionService;
import cn.onlov.admin.pojo.bo.CycleOnlovPermissionBo;
import cn.onlov.admin.service.OnlovPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OnlovPermissionServiceImpl implements OnlovPermissionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IPermissionService iPermissionService;

    @Autowired
    private IRolePermissionService iRolePermissionService;


    @Override
    public IPage<OnlovPermission> selectByPage(CycleOnlovPermissionBo bo) {
        IPage<OnlovPermission> page = new Page<>();
        page.setCurrent(bo.getCurr()).setSize(bo.getPageSize());

        IPage<OnlovPermission> res = iPermissionService.page(page, new QueryWrapper<OnlovPermission>().lambda().orderByDesc(OnlovPermission::getId));
        return res;

    }

    @Override
    @Cacheable(value = "CyclePermissions", key = "'all'")
    public List<OnlovPermission> queryAll() {
        QueryWrapper<OnlovPermission> queryWrapper = new QueryWrapper<>();

        List<OnlovPermission> list = iPermissionService.list(queryWrapper);
        return list;
    }

    @Override
    @Cacheable(value = "permissions", key = "'all_menu'")
    public List<OnlovPermission> queryAllMenu() {
        LambdaQueryWrapper<OnlovPermission> queryWrapper = new QueryWrapper<OnlovPermission>().lambda();
        queryWrapper.in(OnlovPermission::getSystemId, Constants.THIS_SYSTEM_ID ,Constants.SYSTEM_MAIN_ID );
        queryWrapper.eq(OnlovPermission::getType, Constants.MENU_TYPE).orderByAsc(OnlovPermission::getId);
        List<OnlovPermission> list = iPermissionService.list(queryWrapper);
        return list;
    }

    @Override
    @Cacheable(value = "permissions", key = "'list_'+#map['id'].toString()+'_'+#map['type']")
    public List<OnlovPermission> loadUserCyclePermissions(Map<String, Object> map) {
        logger.debug("loadUserPermissions id={},type={}", new Object[]{map.get("id"), map.get("type")});
        int id = (int) map.get("id");
        int type = (int) map.get("type");
        QueryWrapper<OnlovPermission> queryWrapper = new QueryWrapper<>();
        List<OnlovPermission> list = iPermissionService.loadUserPermissions(id, type,Constants.SYSTEM_EVALUATE_ID);
        return list;
    }

    @Override
    public List<OnlovPermission> queryCyclePermissionsListWithSelected(Integer rid) {
        List<OnlovPermission> list = iPermissionService.queryPermissionsListWithSelected(rid ,Constants.SYSTEM_EVALUATE_ID);
        return list;
    }

    @Override
    @Cacheable(value = "permissions", key = "'tree_'+#userId")
    public List<OnlovPermission> loadUserCyclePermissionsTree(Integer userId) {
        return getPermissions(userId);
    }

    @Override
    @CachePut(value = "permissions", key = "'tree_'+#userId")
    public List<OnlovPermission> updateUserCyclePermissionsTree(Integer userId) {
        return getPermissions(userId);
    }

    private List<OnlovPermission> getPermissions(Integer userId) {
        logger.debug("loadUserPermissionsTree userId={}", userId);
        Map<String, Object> map = new HashMap();
        map.put("type", 1);
        map.put("id", userId);
        List<OnlovPermission> userOnlovPermissions = null;
        if (userId == 1) {
            userOnlovPermissions = queryAllMenu();
        } else {
            userOnlovPermissions = loadUserCyclePermissions(map);
        }
        List<OnlovPermission> list = getChildren(userOnlovPermissions, 0);
        return list;
    }


    // 取节点的所有children
    private List<OnlovPermission> getChildren(List<OnlovPermission> results, Integer rootId) {

        List<OnlovPermission> list = new ArrayList();
        for (int i = 0; i < results.size(); i++) {
            OnlovPermission root = results.get(i);
            if (rootId .equals( root.getPid())) {
                List<OnlovPermission> children = this.getChildren(results, root.getId());
                if (!children.isEmpty()) {
                    root.setChildren(children);
                }
                list.add(root);
            }
        }
        return list;
    }

    @Override
    public void deleteByKeys(String[] keys) {
        //删除资源
        if (keys != null) {
            //删除关联数据
            QueryWrapper<OnlovPermission> queryWrapper = new QueryWrapper<>();
            iPermissionService.remove(queryWrapper.lambda().in(OnlovPermission::getId, keys));


            QueryWrapper<OnlovRolePermission> queryWrapperRole = new QueryWrapper<>();
            iRolePermissionService.remove(queryWrapperRole.lambda().in(OnlovRolePermission::getPid, keys));

        }
    }


}
