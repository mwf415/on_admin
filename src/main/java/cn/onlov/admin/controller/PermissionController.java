package cn.onlov.admin.controller;


import cn.onlov.admin.constants.Constants;
import cn.onlov.admin.core.dao.entities.OnlovPermission;
import cn.onlov.admin.core.dao.interfaces.IPermissionService;
import cn.onlov.admin.pojo.bo.CycleOnlovPermissionBo;
import cn.onlov.admin.service.OnlovPermissionService;
import cn.onlov.admin.shiro.ShiroService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Resource
    private OnlovPermissionService onlovPermissionService;
    @Resource
    private IPermissionService iPermissionService;
    @Resource
    private ShiroService shiroService;

    @RequestMapping
    public Map<String,Object> getAll(OnlovPermission permissions, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        CycleOnlovPermissionBo bo  = new CycleOnlovPermissionBo();
        BeanUtils.copyProperties(permissions,bo);
        bo.setCurr(start);
        bo.setPageSize(length);

        IPage<OnlovPermission> pageInfo = onlovPermissionService.selectByPage(bo);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getRecords());
        return map;
    }

    @RequestMapping("/permissionsWithSelected")
    public List<OnlovPermission> permissionsWithSelected(Integer rid){
        return onlovPermissionService.queryCyclePermissionsListWithSelected(rid);
    }

    @RequestMapping("/loadMenu")
    public List<OnlovPermission> loadMenu(){
        Integer userId = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
        List<OnlovPermission> permissionsList = onlovPermissionService.loadUserCyclePermissionsTree(userId);
        return permissionsList;
    }

    @CacheEvict(cacheNames="permissions", allEntries=true)
    @RequestMapping(value = "/add")
    public String add(OnlovPermission onlovPermission){
        try{
            onlovPermission.setSystemId(Constants.SYSTEM_EVALUATE_ID);
            iPermissionService.saveOrUpdate(onlovPermission);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    
    @CacheEvict(cacheNames="permissions", allEntries=true)
    @RequestMapping(value = "/update")
    public String update(OnlovPermission onlovPermission){
        try{
            iPermissionService.saveOrUpdate(onlovPermission);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    
    @CacheEvict(cacheNames="permissions", allEntries=true)
    @RequestMapping(value = "/delete")
    public String delete(String ids){
        try{
        	if(StringUtils.isNotBlank(ids)){
        		onlovPermissionService.deleteByKeys(ids.split(","));
        		//更新权限
        		shiroService.updatePermission();
        		return "success";
        	}
        	 return "fail";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    
}
