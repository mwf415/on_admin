package cn.onlov.admin.service.impl;


import cn.onlov.admin.core.dao.entities.CycleBase;
import cn.onlov.admin.core.dao.interfaces.ICycleBaseService;
import cn.onlov.admin.pojo.bo.CycleBaseBo;
import cn.onlov.admin.service.OnlovBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OnlovBaseServiceImpl implements OnlovBaseService {
    @Resource
    private ICycleBaseService iCycleBaseService;


    @Override
    public IPage<CycleBase> selectByPage(CycleBaseBo bo) {
        IPage<CycleBase> page = new Page<>();
        page.setCurrent((bo.getCurr() / bo.getPageSize()) + 1).setSize(bo.getPageSize());
        LambdaQueryWrapper<CycleBase> lambda = new QueryWrapper<CycleBase>().lambda();
        lambda.orderByDesc(CycleBase::getId);
        IPage<CycleBase> res = iCycleBaseService.page(page, lambda);
        return res;
    }

    @Override
    @Cacheable(value = "bases", key = "'all_base'")
    public List<CycleBase> selectAll() {
        List<CycleBase> res = iCycleBaseService.list(new QueryWrapper<CycleBase>().lambda().orderByDesc(CycleBase::getId));
        return res;
    }

    @Override
    public void deleteByKey(Integer key) {
        iCycleBaseService.remove(new QueryWrapper<CycleBase>().lambda().eq(CycleBase::getId, key));
    }

}
