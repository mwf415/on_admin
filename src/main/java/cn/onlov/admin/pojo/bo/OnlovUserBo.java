package cn.onlov.admin.pojo.bo;

import cn.onlov.admin.core.dao.entities.OnlovUser;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlovUserBo extends OnlovUser implements Serializable {

    private static final long serialVersionUID = 318399374157993931L;
    private int curr = 0; //当前页数
    private int pageSize = 10;// 每页几行



}
