package cn.onlov.admin.controller;

import cn.onlov.admin.core.dao.entities.CycleBase;
import cn.onlov.admin.core.dao.interfaces.ICycleBaseService;
import cn.onlov.admin.pojo.bo.CycleBaseBo;
import cn.onlov.admin.service.OnlovBaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqj on 2017/4/26.
 */
@RestController
@RequestMapping("/bases")
public class BaseController {
    @Resource
    private OnlovBaseService onlovBaseService;
    @Resource
    private ICycleBaseService iCycleBaseService;
    @RequestMapping
    public  Map<String,Object> getAll(CycleBase base, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();
        CycleBaseBo cycleBaseBo  = new CycleBaseBo();
        BeanUtils.copyProperties(base,cycleBaseBo);
        cycleBaseBo.setCurr(start);
        cycleBaseBo.setPageSize(length);
        IPage<CycleBase> pageInfo = onlovBaseService.selectByPage(cycleBaseBo);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getRecords());
        return map;
    }

    @RequestMapping("getAll")
    public  Map<String,Object> getAll(){
        Map<String,Object> map = new HashMap<>();
        boolean success = false;
        String msg = "获取数据失败！";
        Object data = null;

        List<CycleBase> list = null;
        try {
            list = onlovBaseService.selectAll();
            success = true;
            data = list;
            msg = "获取数据成功";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("success", success);
        map.put("data", data);
        map.put("msg", msg);
        return map;
    }

    @RequestMapping(value = "/add")
    public String add(CycleBase base) {
        try {
            iCycleBaseService.save(base);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            onlovBaseService.deleteByKey(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/update")
    public String update(CycleBase base) {
        try {
            iCycleBaseService.saveOrUpdate(base);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }



}
