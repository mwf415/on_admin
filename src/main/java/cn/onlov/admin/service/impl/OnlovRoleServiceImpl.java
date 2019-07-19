package cn.onlov.admin.service.impl;

import cn.onlov.admin.core.dao.entities.OnlovRole;
import cn.onlov.admin.core.dao.interfaces.IRoleService;
import cn.onlov.admin.pojo.bo.OnlovRoleBo;
import cn.onlov.admin.service.OnlovRoleService;
import cn.onlov.constants.Constants;
import cn.onlov.utils.MyPageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlovRoleServiceImpl implements OnlovRoleService {
    @Autowired
    private IRoleService iRoleService;

    @Override
    public List<OnlovRole> queryCycleRoleListWithSelected(Integer uid) {
        IPage<OnlovRole> page = new Page<>();
//        page.setCurrent(bo.getCurr()).setSize(bo.getPageSize());
//        boolean a = OnStringUtils.isNotEmpty(bo.getBaseName());


        return null;
    }

    @Override
    public IPage<OnlovRole> selectByPage(OnlovRoleBo bo) {
        LambdaQueryWrapper<OnlovRole> queryWrapper = new QueryWrapper<OnlovRole>().lambda();
        IPage<OnlovRole> page = new Page<>();
        page.setCurrent(MyPageUtil.currPage(bo.getCurr(),bo.getPageSize())).setSize(bo.getPageSize());
        IPage<OnlovRole> res = iRoleService.page(page, queryWrapper);

        return res;
    }

    @Override
    public void delCycleRole(Integer roleid) {
        QueryWrapper<OnlovRole> queryWrapper =  new QueryWrapper<>();
        iRoleService.remove(queryWrapper.lambda().eq(OnlovRole::getRoleId ,roleid));
    }

    @Override
    public List<OnlovRole> queryCycleRoleListByUserId(Integer userId) {
        QueryWrapper<OnlovRole> queryWrapper =  new QueryWrapper<>();
        List<OnlovRole> onlovRoles = iRoleService.queryRoleListByUserId(userId);
        return onlovRoles;
    }

}
