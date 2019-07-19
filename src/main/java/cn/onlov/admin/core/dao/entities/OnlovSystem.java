package cn.onlov.admin.core.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kaifa
 * @since 2019-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OnlovSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 系统 exam cycle osce等
     */
    private String name;

    private String des;


}
