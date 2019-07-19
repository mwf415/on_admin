package cn.onlov.admin.service.impl;

import cn.onlov.admin.core.dao.entities.CycleRoom;
import cn.onlov.admin.core.dao.entities.OnlovSystem;
import cn.onlov.admin.core.dao.interfaces.IOnlovSystemService;
import cn.onlov.admin.service.OnlovSystemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlovSystemServiceImpl implements OnlovSystemService {

    @Autowired
    private IOnlovSystemService iOnlovSystemService ;

    @Override
    @Cacheable(value = "onlovSystems", key = "'onlov_systems'")
    public List<OnlovSystem> selectAll() {
        LambdaQueryWrapper<OnlovSystem> lambda = new QueryWrapper<OnlovSystem>().lambda();
        List<OnlovSystem> list = iOnlovSystemService.list(lambda);
        return list;
    }
}
