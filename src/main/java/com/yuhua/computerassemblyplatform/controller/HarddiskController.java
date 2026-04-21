package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.yuhua.computerassemblyplatform.common.Result;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yuhua.computerassemblyplatform.service.IHarddiskService;
import com.yuhua.computerassemblyplatform.entity.Harddisk;




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
@RequestMapping("/harddisk")
public class HarddiskController {

    @Autowired
    private IHarddiskService harddiskService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Harddisk harddisk){
        return Result.success(harddiskService.saveOrUpdate(harddisk));
    }

    //查询所有
    @GetMapping
    public List<Harddisk> findAll() {
        return harddiskService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(harddiskService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(harddiskService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String harddiskName, @RequestParam(defaultValue = "") String harddiskType){
        QueryWrapper<Harddisk> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(harddiskName!=null&&!"".equals(harddiskName)){
            queryWrapper.like("harddisk_name",harddiskName);
        }
        if(harddiskType!=null&&!"".equals(harddiskType)){
            queryWrapper.like("harddisk_type",harddiskType);
        }
        return Result.success(harddiskService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Harddisk> harddiskList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Harddisk harddisk=new Harddisk();
            harddisk.setHarddiskName((String) row.get(1));
            harddisk.setHarddiskType((String) row.get(2));
            harddisk.setHarddiskVolume((String) row.get(3));
            harddisk.setHarddiskReadVelocity((String) row.get(4));
            harddisk.setHarddiskMoveVelocity((String) row.get(5));
            harddisk.setHarddiskFactory((String) row.get(6));
            harddisk.setHarddiskPrice((String) row.get(7));
            harddisk.setHarddiskImg((String) row.get(8));
            harddiskList.add(harddisk);
        }
        harddiskService.saveBatch(harddiskList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Harddisk> list = harddiskService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("harddiskName","硬盘名称");
        writer.addHeaderAlias("harddiskType","硬盘类型");
        writer.addHeaderAlias("harddiskVolume","硬盘容量");
        writer.addHeaderAlias("harddiskReadVelocity","硬盘读取速度");
        writer.addHeaderAlias("harddiskMoveVelocity","硬盘传输速度");
        writer.addHeaderAlias("harddiskFactory","硬盘厂商");
        writer.addHeaderAlias("harddiskPrice","硬盘价格");
        writer.addHeaderAlias("harddiskImg","硬盘图片");


        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("硬盘信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

