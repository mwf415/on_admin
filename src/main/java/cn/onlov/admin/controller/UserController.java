package cn.onlov.admin.controller;

import cn.onlov.admin.core.dao.entities.OnlovUser;
import cn.onlov.admin.core.dao.interfaces.IUserService;
import cn.onlov.admin.pojo.bo.OnlovUserBo;
import cn.onlov.admin.service.OnlovUserRoleService;
import cn.onlov.admin.service.OnlovUserService;
import cn.onlov.admin.util.PasswordHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/22.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    private OnlovUserService onlovUserService;
    @Resource
    private IUserService iUserService;

    @Resource
    private OnlovUserRoleService userRoleService;

    @RequestMapping
    public Map<String,Object> getAll(OnlovUser onlovUser, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        OnlovUserBo bo  = new OnlovUserBo();
        BeanUtils.copyProperties(onlovUser,bo);
        bo.setCurr(start);
        bo.setPageSize(length);

        IPage<OnlovUser> pageInfo = onlovUserService.getBusinessPageUser(bo);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getRecords());
        return map;
    }


    /**
     * 保存用户角色
     * @param
     * @return
     */
    @RequestMapping("/saveUserRoles")
    public String saveUserRoles(long uid, Long[] rids){
        if(StringUtils.isEmpty(uid))
            return "error";
        try {
            userRoleService.addUserRole(uid, rids);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(OnlovUser onlovUser) {
        OnlovUser u = onlovUserService.selectByLoginName(onlovUser.getLoginName());
        if(u != null)
            return "error";
        try {
//            onlovUser.setEnable(1);
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(onlovUser);
            onlovUser.setIdentityId(1);
            onlovUser.setStatus(1+"");
            iUserService.save(onlovUser);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id){
      try{
          onlovUserService.delUser(id);
          return "success";
      }catch (Exception e){
          e.printStackTrace();
          return "fail";
      }
    }

    @RequestMapping(value = "/batchSave")
    public String batchSave( String[] loginNames , String[] userNums, String[] real_names,
                             String[] user_pwds,
                             String[] baseNames, String[] roomNames,
                             Integer[] identity_ids, Integer[] grades, Integer[] trainTimes){
      try{
          List<OnlovUser> onlovUserList = onlovUserService.selectByLoginNames(loginNames);
          Map<String,Integer> map = new HashMap<>();
          for (int i = 0; i < loginNames.length; i++) {
            map.put(loginNames[i],i);
          }

          if(onlovUserList.size()>0){
              StringBuilder sb = new StringBuilder();
              for (OnlovUser onlovUser : onlovUserList) {
                  String loginName = onlovUser.getLoginName();
                  Integer i = map.get(loginName);
                 String realName = real_names[i];
                  sb.append(realName+";");
              }
              return sb.toString();
          }

          for (int i = 0; i < loginNames.length; i++) {
             String loginName = loginNames[i];
             String userNum = userNums[i];
             String real_name = real_names[i];
             String user_pwd = user_pwds[i];
             String baseName = baseNames[i];
             String roomName = roomNames[i];
             Integer identity_id = identity_ids[i];
             Integer grade = grades[i];
             int trainTime = trainTimes[i];
             OnlovUser onlovUser = new OnlovUser();
             onlovUser.setLoginName(loginName);
              onlovUser.setUserNum(userNum);
              onlovUser.setRealName(real_name);

              onlovUser.setUserPwd(user_pwd.trim());
              onlovUser.setBaseName(baseName);
              onlovUser.setRoomName(roomName);
              onlovUser.setIdentityId(identity_id);
              onlovUser.setGrade(grade);
              onlovUser.setTrainTime(trainTime);
              onlovUser.setStatus(1+"");
              PasswordHelper passwordHelper = new PasswordHelper();
              passwordHelper.encryptPassword(onlovUser);
              iUserService.save(onlovUser);
          }

          return "success";
      }catch (Exception e){
          e.printStackTrace();
          return "fail";
      }
    }

}
