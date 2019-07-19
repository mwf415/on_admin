package cn.onlov.admin.service;

import cn.onlov.admin.core.dao.entities.OnlovUser;
import cn.onlov.admin.pojo.bo.OnlovUserBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface OnlovUserService {

	IPage<OnlovUser> getBusinessPageUser(OnlovUserBo bo) ;
	
	OnlovUser selectByLoginName(String loginName);
	
	void delUser(Integer userid);
	
	List<OnlovUser> selectByLoginNames(String[] loginNames);

}
