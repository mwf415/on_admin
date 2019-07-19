package cn.onlov.admin.service;

import cn.onlov.admin.core.dao.entities.CycleRoom;
import cn.onlov.admin.pojo.bo.CycleRoomBo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * Created by yangqj on 2017/4/25.
 */
public interface OnlovRoomService {
	
	IPage<CycleRoom> selectByPage(CycleRoomBo base);
	List<CycleRoom> selectAll();
	void deleteByKey(Integer key);
}
