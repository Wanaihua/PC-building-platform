package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Keyboard;
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

import com.yuhua.computerassemblyplatform.service.IKeyboardService;
import com.yuhua.computerassemblyplatform.entity.Keyboard;




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
@RequestMapping("/keyboard")
public class KeyboardController {

    @Autowired
    private IKeyboardService keyboardService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Keyboard keyboard){
        return Result.success(keyboardService.saveOrUpdate(keyboard));
    }

    //查询所有
    @GetMapping
    public List<Keyboard> findAll() {
        return keyboardService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(keyboardService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(keyboardService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String keyboardName, @RequestParam(defaultValue = "") String keyboardFactory, @RequestParam(defaultValue = "") String keyboardType, @RequestParam(defaultValue = "") String keyboardColor){
        QueryWrapper<Keyboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!keyboardName.equals("")){
            queryWrapper.like("keyboard_name",keyboardName);
        }
        if(!keyboardFactory.equals("")){
            queryWrapper.like("keyboard_factory",keyboardFactory);
        }
        if(!keyboardType.equals("")){
            queryWrapper.like("keyboard_type",keyboardType);
        }
        if(!keyboardColor.equals("")){
            queryWrapper.like("keyboard_color",keyboardColor);
        }
        return Result.success(keyboardService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Keyboard> keyboardList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Keyboard keyboard=new Keyboard();
            keyboard.setKeyboardName((String) row.get(1));
            keyboard.setKeyboardPrice((String) row.get(2));
            keyboard.setKeyboardFactory((String) row.get(3));
            keyboard.setKeyboardType((String) row.get(4));
            keyboard.setKeyboardImg((String) row.get(5));
            keyboard.setKeyboardColor((String) row.get(6));

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = new Date();
                keyboard.setKeyboardTime(sdf.parse(sdf.format(date)));
            }catch (Exception e){
                e.printStackTrace();
            }
            keyboardList.add(keyboard);
        }
        keyboardService.saveBatch(keyboardList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Keyboard> list = keyboardService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("keyboard_name","键盘名称");
        writer.addHeaderAlias("keyboard_price","键盘价格");
        writer.addHeaderAlias("keyboard_factory","键盘厂商");
        writer.addHeaderAlias("keyboard_type","键盘类型");
        writer.addHeaderAlias("keyboard_img","键盘图片");
        writer.addHeaderAlias("keyboard_color","键盘轴承");



        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("键盘信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

