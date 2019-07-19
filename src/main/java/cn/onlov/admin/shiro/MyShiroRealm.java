package cn.onlov.admin.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.onlov.admin.core.dao.entities.OnlovPermission;
import cn.onlov.admin.core.dao.entities.OnlovUser;
import cn.onlov.admin.service.OnlovPermissionService;
import cn.onlov.admin.service.OnlovUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
/**
 * Created by yangqj on 2017/4/21.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private OnlovUserService cycleOnlovUserService;

    @Resource
    private OnlovPermissionService onlovPermissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        OnlovUser onlovUser = (OnlovUser) SecurityUtils.getSubject().getPrincipal();//OnlovUser{id=1, username='admin', password='3ef7164d1f6167cb9f2658c07d3c2f0a', enable=1}
        Integer userId = onlovUser.getId();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",userId);
        List<OnlovPermission> loadUserOnlovPermissions = null;
        if(userId == 1){
        	loadUserOnlovPermissions = onlovPermissionService.queryAll();
        }else{
        	loadUserOnlovPermissions = onlovPermissionService.loadUserCyclePermissions(map);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(OnlovPermission permissions : loadUserOnlovPermissions){
            info.addStringPermission(permissions.getUrl());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    	//获取用户的输入的账号.
        String loginName = (String)authcToken.getPrincipal();
        OnlovUser onlovUser = cycleOnlovUserService.selectByLoginName(loginName);
        if(onlovUser ==null) throw new UnknownAccountException();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                onlovUser, //用户
                onlovUser.getUserPwd(), //密码
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", onlovUser);
        session.setAttribute("userSessionId", onlovUser.getId());
        return authenticationInfo;
    }

    /**
     * 指定principalCollection 清除
     */
  /*  public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {

        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
*/
}
