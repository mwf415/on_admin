package cn.onlov.admin.controller;

import cn.onlov.admin.core.dao.entities.*;
import cn.onlov.admin.core.dao.impl.IOnlovSystemServiceImpl;
import cn.onlov.admin.core.dao.interfaces.IOnlovSystemService;
import cn.onlov.admin.service.*;
import cn.onlov.utils.OnStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yangqj on 2017/4/21.
 */
@Controller
public class IndexController {


	@Resource
	private OnlovUserService cycleOnlovUserService;
	@Resource
	private OnlovRoleService onlovRoleService;
	@Resource
	private OnlovBaseService onlovBaseService;
    @Resource
    private OnlovRoomService onlovRoomService;


    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpServletRequest request, OnlovUser onlovUser, Model model){
        if ( !OnStringUtils.isNotEmpty(onlovUser.getLoginName()) || !OnStringUtils.isNotEmpty(onlovUser.getUserPwd())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "/login";
        }
        OnlovUser u = cycleOnlovUserService.selectByLoginName(onlovUser.getLoginName());
        if(u==null || u.getIdentityId()!= onlovUser.getIdentityId()){
            request.setAttribute("msg", "用户不存在！");
            return "/login";
        }
        UsernamePasswordToken token = null;
        try {
        	Subject subject = SecurityUtils.getSubject();
        	if(onlovUser.getUserPwd()!=null){
        		token=new UsernamePasswordToken(onlovUser.getLoginName(), onlovUser.getUserPwd());
        		subject.login(token);
        		if(onlovUser.getIdentityId()==2)//如果是学生，跳转到学生页面
        			return "redirect:/myExams/examsPage";
        		return "redirect:usersPage";
        	}
        }catch (LockedAccountException lae) {
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
        } catch (AuthenticationException e) {
            request.setAttribute("msg", "用户或密码不正确！");
        }
        
        if(token!=null){
        	token.clear();
        }
        return "login";
        
        
    }


    @RequestMapping(value="/usersPage")
    public String usersPage(Model model){
        List<CycleBase> bases = onlovBaseService.selectAll();
        List<CycleRoom> rooms = onlovRoomService.selectAll();
        model.addAttribute("bases",bases);
        model.addAttribute("rooms",rooms);

        return "user/users";
    }

    @RequestMapping("/rolesPage")
    public String rolesPage(Model model){
        List<OnlovSystem> onlovSystems = onlovSystemService.selectAll();
        model.addAttribute("onlovSystems", onlovSystems);
        return "role/roles";
    }

    @Autowired
    private OnlovSystemService onlovSystemService ;

    @RequestMapping("/permissionsPage")
    public String permissionsPage(Model model){

        List<OnlovSystem> onlovSystems = onlovSystemService.selectAll();
        model.addAttribute("onlovSystems", onlovSystems);

        return "resources/resources";
    }
    


    @RequestMapping("/basesPage")
    public String baseStationItemsPage(Model model){
    	List<CycleBase> bases = onlovBaseService.selectAll();
    	model.addAttribute("bases", bases);
        return "/base/bases";
    }

    @RequestMapping("/roomsPage")
    public String roomStationItemsPage(Model model){
        List<CycleRoom> rooms = onlovRoomService.selectAll();
        model.addAttribute("rooms", rooms);
        return "/room/rooms";
    }


    @RequestMapping("/403")
    public String forbidden(){
        return "403";
    }


    /**
     * 评估关系表
     * @return
     */

    @RequestMapping("/evaluateRelatePage")
    public String relate(){
        return "relate/index";
    }

    /**
     * 评估分类
     * @return
     */
    @RequestMapping("/evaluateCcatalogPage")
    public String catalog(){
        return "catalog/index";
    }

    /**
     * 评估项
     * @return
     */
    @RequestMapping("/evaluateItemPage")
    public String item(){

        return "item/index";
    }

    /**
     * 评估表
     * @return
     */
    @RequestMapping("/evaluateTablePage")
    public String table(){
        return "table/index";
    }

    /**
     * 创建评估
     * @return
     */

    @RequestMapping("/evaluateCreatePage")
    public String createe(){
        return "create/index";
    }

    /**
     * 查看已评估，待评估
     * @return
     */
    @RequestMapping("/evaluate/load/page")
    public String load(){
        return "load/index";
    }

    /**
     * 汇总统计
     * @return
     */
 @RequestMapping("/evaluate/sum/page")
    public String sum(){
        return "sum/index";
    }


}
