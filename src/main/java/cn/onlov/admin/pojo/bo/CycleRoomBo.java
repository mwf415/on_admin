package cn.onlov.admin.pojo.bo;


import cn.onlov.admin.core.dao.entities.CycleRoom;
import lombok.Data;

@Data
public class CycleRoomBo extends CycleRoom {
    private static final long serialVersionUID = 318399374157993931L;
    private int curr = 0; //当前页数
    private int pageSize = 10;// 每页几行

}
