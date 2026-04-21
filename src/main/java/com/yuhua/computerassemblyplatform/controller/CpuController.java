package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Memorysticks;
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

import com.yuhua.computerassemblyplatform.service.ICpuService;
import com.yuhua.computerassemblyplatform.entity.Cpu;




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
@RequestMapping("/cpu")
public class CpuController {

    @Autowired
    private ICpuService cpuService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Cpu cpu){
        return Result.success(cpuService.saveOrUpdate(cpu));
    }

    //查询所有
    @GetMapping
    public List<Cpu> findAll() {
        return cpuService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(cpuService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(cpuService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String cpuName,@RequestParam(defaultValue = "") String cpuProcessor){
        QueryWrapper<Cpu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(cpuName!=null&&!"".equals(cpuName)){
            queryWrapper.like("cpu_name",cpuName);
        }
        if(cpuProcessor!=null&&!"".equals(cpuProcessor)){
            queryWrapper.like("cpu_processor",cpuProcessor);
        }
        return Result.success(cpuService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException {
        List<Cpu> list = cpuService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("cpuName", "CPU名称");
        writer.addHeaderAlias("cpuFactory", "CPU厂商");
        writer.addHeaderAlias("cpuPrice", "CPU价格");
        writer.addHeaderAlias("cpuImg", "CPU图片");
        writer.addHeaderAlias("cpuThread", "CPU线程");
        writer.addHeaderAlias("cpuProcessor", "CPU处理器");
        writer.addHeaderAlias("cpuNucleus", "CPU核心");

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
        List<Cpu> graphicsList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Cpu cpu=new Cpu();
            cpu.setCpuName(row.get(1).toString());
            cpu.setCpuFactory(row.get(2).toString());
            cpu.setCpuPrice(row.get(3).toString());
            cpu.setCpuImg(row.get(4).toString());
            cpu.setCpuThread(row.get(5).toString());
            cpu.setCpuProcessor(row.get(6).toString());
            cpu.setCpuNucleus(row.get(7).toString());
            graphicsList.add(cpu);
        }
        cpuService.saveBatch(graphicsList);
        return true;
    }
}

