package cn.onlov.admin.service;

import cn.onlov.admin.core.dao.entities.CycleBase;
import cn.onlov.admin.pojo.bo.CycleBaseBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Created by yangqj on 2017/4/25.
 */
public interface OnlovBaseService {
	IPage<CycleBase> selectByPage(CycleBaseBo base);
	List<CycleBase> selectAll();
	void deleteByKey(Integer key);
	//根据基地获取轮转科室
}
