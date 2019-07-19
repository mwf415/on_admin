package cn.onlov.admin.controller;

import cn.onlov.admin.core.dao.entities.OnlovRole;
import cn.onlov.admin.core.dao.interfaces.IRoleService;
import cn.onlov.admin.pojo.bo.OnlovRoleBo;
import cn.onlov.admin.service.OnlovRolePermissionService;
import cn.onlov.admin.service.OnlovRoleService;
import cn.onlov.constants.Constants;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/26.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Resource
    private OnlovRoleService onlovRoleService;
    @Resource
    private OnlovRolePermissionService onlovRolePermissionService;


    @Autowired
    private IRoleService iRoleService;

    @RequestMapping
    public  Map<String,Object> getAll(OnlovRole role, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();

        OnlovRoleBo bo  = new OnlovRoleBo();
        BeanUtils.copyProperties(role,bo);
        bo.setCurr(start);
        bo.setPageSize(length);

        IPage<OnlovRole> pageInfo = onlovRoleService.selectByPage(bo);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getRecords());
        return map;
    }

    @RequestMapping("/rolesWithSelected")
    public List<OnlovRole> rolesWithSelected(Integer uid){
        return onlovRoleService.queryCycleRoleListWithSelected(uid);
    }

    //分配角色
    @RequestMapping("/saveRolePermissions")
    public String saveRolePermission(Integer rid, Integer[] pids){
        if(StringUtils.isEmpty(rid))
            return "error";
        try {
            onlovRolePermissionService.addRolePermission(rid, pids);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(OnlovRole role) {
        try {
            // 系统级别单独创建 systemID为  系统的ID
            role.setSystemId(Constants.SYSTEM_EVALUATE_ID);
            iRoleService.saveOrUpdate(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            iRoleService.removeById(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }



}
