package cn.onlov.admin.service.impl;

import cn.onlov.admin.core.dao.entities.OnlovUser;
import cn.onlov.admin.core.dao.interfaces.IUserService;
import cn.onlov.admin.pojo.bo.OnlovUserBo;
import cn.onlov.admin.service.OnlovUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class OnlovUserServiceImpl implements OnlovUserService {
    @Resource
    private IUserService iUserService;


    @Override
    public IPage<OnlovUser> getBusinessPageUser(OnlovUserBo bo) {
        IPage<OnlovUser> page = new Page<>();
        page.setCurrent((bo.getCurr()/bo.getPageSize())+1).setSize(bo.getPageSize());
        IPage<OnlovUser> res = iUserService.page(page, new QueryWrapper<OnlovUser>().lambda()
                .like(MyStringUtils.isNotEmpty(bo.getRealName()), OnlovUser:: getRealName , bo.getRealName())
                .eq(MyStringUtils.isNotEmpty(bo.getBaseName()), OnlovUser:: getBaseName, bo.getBaseName())
                .eq(MyStringUtils.isNotEmpty(bo.getRoomName()), OnlovUser::getRoomName,bo.getRoomName())
//                .eq(MyStringUtils.isNotEmpty(bo.getGrade()),OnlovUser::getGrade, bo.getGrade())
//                .eq(MyStringUtils.isNotEmpty(bo.getIdentityId()),OnlovUser::getIdentityId,bo.getIdentityId())
//                .eq(MyStringUtils.isNotEmpty(bo.getIsAt()),OnlovUser::getIsAt,bo.getIsAt())
                .orderByDesc(OnlovUser::getId));
        return res;
    }





    @Override
    public OnlovUser selectByLoginName(String loginName) {
        OnlovUser onlovUser = (OnlovUser)iUserService.getObj(new QueryWrapper<OnlovUser>().lambda().eq(OnlovUser::getLoginName,loginName));

        return onlovUser;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delUser(Integer userid) {

        iUserService.removeById(userid);
    }

    @Override
    public List<OnlovUser> selectByLoginNames(String[] loginNames) {
        List<OnlovUser> list = iUserService.list(new QueryWrapper<OnlovUser>().lambda().in(OnlovUser::getLoginName, loginNames));

        return list;
    }






}
