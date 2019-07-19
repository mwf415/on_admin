package cn.onlov.admin.core.dao.interfaces;

import cn.onlov.admin.core.dao.entities.OnlovRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kaifa
 * @since 2019-01-04
 */
public interface IRoleService extends IService<OnlovRole> {

    List<OnlovRole> queryRoleListByUserId(Integer roleId);

}
