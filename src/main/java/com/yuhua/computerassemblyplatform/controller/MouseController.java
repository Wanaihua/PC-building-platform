package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Audio;
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

import com.yuhua.computerassemblyplatform.service.IMouseService;
import com.yuhua.computerassemblyplatform.entity.Mouse;




import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/mouse")
public class MouseController {

    @Autowired
    private IMouseService mouseService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Mouse mouse){
        return Result.success(mouseService.saveOrUpdate(mouse));
    }

    //查询所有
    @GetMapping
    public List<Mouse> findAll() {
        return mouseService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(mouseService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(mouseService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String mouseName, @RequestParam(defaultValue = "") String mouseFactory, @RequestParam(defaultValue = "") String mouseType){
        QueryWrapper<Mouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!mouseName.equals("")){
            queryWrapper.like("mouse_name",mouseName);
        }
        if(!mouseFactory.equals("")){
            queryWrapper.like("mouse_factory",mouseFactory);
        }
        if(!mouseType.equals("")){
            queryWrapper.like("mouse_type",mouseType);
        }
        return Result.success(mouseService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Mouse> mouseList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Mouse mouse=new Mouse();
            mouse.setMouseName((String) row.get(1));
            mouse.setMousePrice((String) row.get(2));
            mouse.setMouseFactory((String) row.get(3));
            mouse.setMouseType((String) row.get(4));
            mouse.setMouseImg((String) row.get(5));

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = new Date();
                mouse.setMouseTime(sdf.parse(sdf.format(date)));
            }catch (Exception e){
                e.printStackTrace();
            }
            mouseList.add(mouse);
        }
        mouseService.saveBatch(mouseList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Mouse> list = mouseService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("mouseName","鼠标名称");
        writer.addHeaderAlias("mousePrice","鼠标价格");
        writer.addHeaderAlias("mouseFactory","鼠标厂商");
        writer.addHeaderAlias("mouseType","鼠标类型");
        writer.addHeaderAlias("mouseImg","鼠标图片");




        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("鼠标信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

