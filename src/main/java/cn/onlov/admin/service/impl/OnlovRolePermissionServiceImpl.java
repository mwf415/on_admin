package cn.onlov.admin.service.impl;

import cn.onlov.admin.core.dao.entities.OnlovRolePermission;
import cn.onlov.admin.core.dao.interfaces.IRolePermissionService;
import cn.onlov.admin.service.OnlovRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OnlovRolePermissionServiceImpl implements OnlovRolePermissionService {
   @Autowired
   private IRolePermissionService iRolePermissionService;


	@Override
    //更新权限
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    @CacheEvict(cacheNames="permissions", allEntries=true)
    public void addRolePermission(Integer rid, Integer[] pids){

		QueryWrapper<OnlovRolePermission> queryWrapper =  new QueryWrapper<>() ;
		iRolePermissionService.remove(queryWrapper.lambda().eq(OnlovRolePermission::getRid,rid));
        //添加
        for(Integer pid: pids){
			OnlovRolePermission record = new OnlovRolePermission();
        	record.setRid(rid);
        	record.setPid(pid);
        	iRolePermissionService.save(record);
        }
	}

	@Override
	public void deleteByPermissionKeys(String[] ids) {
		QueryWrapper<OnlovRolePermission> queryWrapper =  new QueryWrapper<>() ;
		iRolePermissionService.remove(queryWrapper.lambda().in(OnlovRolePermission::getPid,ids));
	}
}
