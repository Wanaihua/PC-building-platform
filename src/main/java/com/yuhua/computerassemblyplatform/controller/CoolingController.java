package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Harddisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.yuhua.computerassemblyplatform.common.Result;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yuhua.computerassemblyplatform.service.ICoolingService;
import com.yuhua.computerassemblyplatform.entity.Cooling;




import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YuHua
 * @since 2024-01-03
 */
@RestController
@RequestMapping("/cooling")
public class CoolingController {

    @Autowired
    private ICoolingService coolingService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Cooling cooling){
        return Result.success(coolingService.saveOrUpdate(cooling));
    }

    //查询所有
    @GetMapping
    public List<Cooling> findAll() {
        return coolingService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(coolingService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(coolingService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String coolingName,@RequestParam(defaultValue = "") String coolingWay,@RequestParam(defaultValue = "") String coolingFactory){
        QueryWrapper<Cooling> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(coolingName!=null&&!"".equals(coolingName)){
            queryWrapper.like("cooling_name",coolingName);
        }
        if(coolingWay!=null&&!"".equals(coolingWay)){
            queryWrapper.like("cooling_way",coolingWay);
        }
        if(coolingFactory!=null&&!"".equals(coolingFactory)){
            queryWrapper.like("cooling_factory",coolingFactory);
        }
        return Result.success(coolingService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Cooling> coolingList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Cooling cooling=new Cooling();
            cooling.setId(Integer.valueOf(row.get(0).toString()));
            cooling.setCoolingName(row.get(1).toString());
            cooling.setCoolingWay(row.get(2).toString());
            cooling.setCoolingHeatPipes(row.get(3).toString());
            cooling.setCoolingPrice(row.get(4).toString());
            cooling.setCoolingFactory(row.get(5).toString());
            cooling.setCoolingImg(row.get(6).toString());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = new Date();
                cooling.setCoolingTime(sdf.parse(sdf.format(date)));
            }catch (Exception e){
                e.printStackTrace();
            }
            coolingList.add(cooling);
        }
        coolingService.saveBatch(coolingList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Cooling> list = coolingService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("coolingName","散热器名称");
        writer.addHeaderAlias("coolingWay","散热器方式");
        writer.addHeaderAlias("coolingHeatPipes","散热器热管");
        writer.addHeaderAlias("coolingPrice","散热器价格");
        writer.addHeaderAlias("coolingFactory","散热器厂商");
        writer.addHeaderAlias("coolingImg","散热器图片");
        writer.addHeaderAlias("coolingTime","散热器创建时间");



        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("散热器信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

