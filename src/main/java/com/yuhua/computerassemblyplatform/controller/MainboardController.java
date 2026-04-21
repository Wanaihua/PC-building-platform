package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Graphics;
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

import com.yuhua.computerassemblyplatform.service.IMainboardService;
import com.yuhua.computerassemblyplatform.entity.Mainboard;




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
@RequestMapping("/mainboard")
public class MainboardController {

    @Autowired
    private IMainboardService mainboardService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Mainboard mainboard){
        return Result.success(mainboardService.saveOrUpdate(mainboard));
    }

    //查询所有
    @GetMapping
    public List<Mainboard> findAll() {
        return mainboardService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(mainboardService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(mainboardService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String mainboardName,@RequestParam(defaultValue = "") String mainboardFactory){
        QueryWrapper<Mainboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(mainboardName!=null&&!"".equals(mainboardName)){
            queryWrapper.like("mainboard_name",mainboardName);
        }
        if(mainboardFactory!=null&&!"".equals(mainboardFactory)){
            queryWrapper.like("mainboard_factory",mainboardFactory);
        }
        return Result.success(mainboardService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Mainboard> mainboardList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Mainboard mainboard=new Mainboard();
            mainboard.setMainboardName(row.get(1).toString());
            mainboard.setMainboardFactory(row.get(2).toString());
            mainboard.setMainboardPrice(row.get(3).toString());
            mainboard.setMainboardType(row.get(4).toString());
            mainboard.setMainboardImg(row.get(5).toString());
            mainboard.setMainboardMemorySticksSlot(row.get(6).toString());
            mainboardList.add(mainboard);
        }
        mainboardService.saveBatch(mainboardList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Mainboard> list = mainboardService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","id");
        writer.addHeaderAlias("mainboardName","主板名称");
        writer.addHeaderAlias("mainboardFactory","主板厂商");
        writer.addHeaderAlias("mainboardPrice","主板价格");
        writer.addHeaderAlias("mainboardType","主板类型");
        writer.addHeaderAlias("mainboardImg","主板图片");
        writer.addHeaderAlias("mainboardMemorySticksSlot","主板内存插槽");


        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("主板信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

