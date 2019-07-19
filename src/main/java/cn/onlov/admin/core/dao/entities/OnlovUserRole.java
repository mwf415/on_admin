package cn.onlov.admin.core.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kaifa
 * @since 2019-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OnlovUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 用户id
     */
         private Long uid;

    private Long rid;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


}
