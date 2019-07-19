package cn.onlov.admin.service.impl;


import cn.onlov.admin.core.dao.entities.CycleRoom;
import cn.onlov.admin.core.dao.interfaces.ICycleRoomService;
import cn.onlov.admin.pojo.bo.CycleRoomBo;
import cn.onlov.admin.service.OnlovRoomService;
import cn.onlov.utils.OnStringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OnlovRoomServiceImpl implements OnlovRoomService {
	@Autowired
	private ICycleRoomService iCycleRoomService;


	@Override
	public IPage<CycleRoom> selectByPage(CycleRoomBo bo) {

		IPage<CycleRoom> page = new Page<>();
		page.setCurrent((bo.getCurr()/bo.getPageSize())+1).setSize(bo.getPageSize());
		LambdaQueryWrapper<CycleRoom> queryWrapper = new QueryWrapper<CycleRoom>().lambda();
		boolean notEmpty = OnStringUtils.isNotEmpty(bo.getValue());
		queryWrapper.like(notEmpty,CycleRoom::getValue,bo.getValue());
		queryWrapper.orderByDesc(CycleRoom::getId);
		IPage<CycleRoom> res = iCycleRoomService.page(page, queryWrapper);
		return res;

}

	@Override
	@Cacheable(value = "rooms", key = "'all_room'")
	public List<CycleRoom> selectAll() {

		QueryWrapper<CycleRoom> queryWrapper = new QueryWrapper<>();
		List<CycleRoom> list = iCycleRoomService.list(queryWrapper);

		return list;
	}

	@Override
	public void deleteByKey(Integer key) {
		iCycleRoomService.removeById(key);

	}




}
