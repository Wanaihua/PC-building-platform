package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Mainboard;
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

import com.yuhua.computerassemblyplatform.service.IMemorysticksService;
import com.yuhua.computerassemblyplatform.entity.Memorysticks;




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
@RequestMapping("/memorysticks")
public class MemorysticksController {

    @Autowired
    private IMemorysticksService memorysticksService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Memorysticks memorysticks){
        return Result.success(memorysticksService.saveOrUpdate(memorysticks));
    }

    //查询所有
    @GetMapping
    public List<Memorysticks> findAll() {
        return memorysticksService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(memorysticksService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(memorysticksService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String memorySticksName,@RequestParam(defaultValue = "") String memorySticksFactory){
        QueryWrapper<Memorysticks> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(memorySticksName!=null&&!memorySticksName.equals("")){
            queryWrapper.like("memory_sticks_name",memorySticksName);
        }
        if(memorySticksFactory!=null&&!memorySticksFactory.equals("")){
            queryWrapper.like("memory_sticks_factory",memorySticksFactory);
        }
        return Result.success(memorysticksService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException {
        List<Memorysticks> list = memorysticksService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("memorySticksName", "内存条名称");
        writer.addHeaderAlias("memorySticksFactory", "内存条厂商");
        writer.addHeaderAlias("memorySticksType", "内存条类型");
        writer.addHeaderAlias("memorySticksMemory", "内存条容量");
        writer.addHeaderAlias("memorySticksPrice", "内存条价格");
        writer.addHeaderAlias("memorySticksImg", "内存条图片");

        writer.write(list, true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("内存条信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        out.close();
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Memorysticks> memorysticksList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Memorysticks memorysticks=new Memorysticks();
            memorysticks.setMemorySticksName(row.get(1).toString());
            memorysticks.setMemorySticksFactory(row.get(2).toString());
            memorysticks.setMemorySticksType(row.get(3).toString());
            memorysticks.setMemorySticksMemory(row.get(4).toString());
            memorysticks.setMemorySticksPrice(row.get(5).toString());
            memorysticks.setMemorySticksImg(row.get(6).toString());
            memorysticksList.add(memorysticks);
        }
        memorysticksService.saveBatch(memorysticksList);
        return true;
    }
}

