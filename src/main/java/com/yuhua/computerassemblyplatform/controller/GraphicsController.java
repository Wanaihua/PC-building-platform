package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import com.yuhua.computerassemblyplatform.service.IGraphicsService;
import com.yuhua.computerassemblyplatform.entity.Graphics;




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
@RequestMapping("/graphics")
public class GraphicsController {

    @Autowired
    private IGraphicsService graphicsService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Graphics graphics){
        return Result.success(graphicsService.saveOrUpdate(graphics));
    }

    //查询所有
    @GetMapping
    public List<Graphics> findAll() {
        return graphicsService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(graphicsService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(graphicsService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String graphicsName,@RequestParam(defaultValue = "") String graphicsFactory){
        QueryWrapper<Graphics> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!"".equals(graphicsName)) {
            queryWrapper.like("graphics_name",graphicsName);
        }
        if(!"".equals(graphicsFactory)) {
            queryWrapper.like("graphics_factory",graphicsFactory);
        }
        return Result.success(graphicsService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Graphics> list = graphicsService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","id");
        writer.addHeaderAlias("graphicsName","显卡名称");
        writer.addHeaderAlias("graphicsFactory","显卡厂商");
        writer.addHeaderAlias("graphicsPrice","显卡价格");
        writer.addHeaderAlias("graphicsMemory","显存容量");
        writer.addHeaderAlias("graphicsImg","显卡图片");
        writer.addHeaderAlias("graphicsLight","灯光");
        writer.addHeaderAlias("graphicsBitWidth","显存位宽");
        writer.addHeaderAlias("graphicsFan","显卡风扇");

        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("显卡信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

    //导入
    @PostMapping("/import")
    public boolean importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Graphics> graphicsList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Graphics graphics = new Graphics();
            graphics.setGraphicsName(row.get(1).toString());
            graphics.setGraphicsFactory(row.get(2).toString());
            graphics.setGraphicsPrice(row.get(3).toString());
            graphics.setGraphicsMemory(row.get(4).toString());
            graphics.setGraphicsImg(row.get(5).toString());
            graphics.setGraphicsLight(row.get(6).toString());
            graphics.setGraphicsBitWidth(row.get(7).toString());
            graphics.setGraphicsFan(row.get(8).toString());
            graphicsList.add(graphics);
        }
        graphicsService.saveBatch(graphicsList);
        return true;
    }

}

