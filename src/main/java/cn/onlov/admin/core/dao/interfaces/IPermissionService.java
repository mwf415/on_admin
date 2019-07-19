package cn.onlov.admin.core.dao.interfaces;

import cn.onlov.admin.core.dao.entities.OnlovPermission;
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
public interface IPermissionService extends IService<OnlovPermission> {

    List<OnlovPermission> loadUserPermissions(int id, int type,int systemId);
    List<OnlovPermission> queryPermissionsListWithSelected(int rid,int systemId);


}
