package cn.onlov.admin.core.dao.mapper;

import cn.onlov.admin.core.dao.entities.OnlovPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kaifa
 * @since 2019-01-04
 */
public interface PermissionMapper extends BaseMapper<OnlovPermission> {

    @Select(    "SELECT distinct(cp.id),cp.name,cp.url,cp.pid, cp.type, cp.sort FROM onlov_permission cp   " +
            "     LEFT JOIN onlov_role_permission crp     " +
            "   ON cp.id = crp.pid     " +
            "   LEFT JOIN onvlo_cycle_user_role cur      " +
            "  ON crp.rid =cur.rid     " +
            "   WHERE system_id = #{systemId}      AND cur.uid=#{id}             AND cp.type= #{type}        ORDER BY cp.sort"
            )
    List<OnlovPermission> loadUserPermissions(@Param("id") int id, @Param("type") int type ,@Param("systemId") int systemId);


    @Select("SELECT p.id,p.name,p.pid,p.url,p.type,  (CASE WHEN EXISTS(SELECT 1    FROM onvlov_role_permission rp WHERE rp.pid=p.id AND rp.rid=#{rid}) " +
            "  THEN 'true' ELSE 'false' END) AS checked     FROM onlov_permission p     WHERE system_id = #{systemId}" +
            "   AND p.id!=1     ORDER BY p.sort")
    List<OnlovPermission> queryPermissionsListWithSelected(int rid ,int systemId);



}
