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
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yuhua.computerassemblyplatform.service.IPowerService;
import com.yuhua.computerassemblyplatform.entity.Power;




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
@RequestMapping("/power")
public class PowerController {

    @Autowired
    private IPowerService powerService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Power power){
        return Result.success(powerService.saveOrUpdate(power));
    }

    //查询所有
    @GetMapping
    public List<Power> findAll() {
        return powerService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(powerService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(powerService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String powerName,@RequestParam(defaultValue = "") String powerFactory){
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(powerName!=null&&!"".equals(powerName)){
            queryWrapper.like("power_name",powerName);
        }
        if(powerFactory!=null&&!"".equals(powerFactory)){
            queryWrapper.like("power_factory",powerFactory);
        }
        return Result.success(powerService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Power> powerList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Power power=new Power();
            power.setPowerName((String) row.get(1));
            power.setPowerType((String) row.get(2));
            power.setPowerPower((String) row.get(3));
            power.setPowerPrice((String) row.get(4));
            power.setPowerFactory((String) row.get(5));
            power.setPowerImg((String) row.get(6));
            powerList.add(power);
        }
        powerService.saveBatch(powerList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Power> list = powerService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("powerName","电源名称");
        writer.addHeaderAlias("powerType","电源类型");
        writer.addHeaderAlias("powerPower","电源功率");
        writer.addHeaderAlias("powerPrice","电源价格");
        writer.addHeaderAlias("powerFactory","电源厂商");
        writer.addHeaderAlias("powerImg","电源图片");



        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("电源信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }


}

