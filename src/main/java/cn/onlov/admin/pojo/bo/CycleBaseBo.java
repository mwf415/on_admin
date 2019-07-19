package cn.onlov.admin.pojo.bo;

import cn.onlov.admin.core.dao.entities.CycleBase;
import lombok.Data;

@Data
public class CycleBaseBo extends CycleBase {
    private static final long serialVersionUID = 318399374157993931L;
    private int curr = 0; //当前页数
    private int pageSize = 10;// 每页几行
}
