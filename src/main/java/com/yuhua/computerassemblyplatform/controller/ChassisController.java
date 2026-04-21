package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Cooling;
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

import com.yuhua.computerassemblyplatform.service.IChassisService;
import com.yuhua.computerassemblyplatform.entity.Chassis;




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
@RequestMapping("/chassis")
public class ChassisController {

    @Autowired
    private IChassisService chassisService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Chassis chassis){
        return Result.success(chassisService.saveOrUpdate(chassis));
    }

    //查询所有
    @GetMapping
    public List<Chassis> findAll() {
        return chassisService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(chassisService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(chassisService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String chassisName,@RequestParam(defaultValue = "") String chassisFactory,@RequestParam(defaultValue = "") String chassisType){
        QueryWrapper<Chassis> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(chassisName!=null&&!"".equals(chassisName)){
            queryWrapper.like("chassis_name",chassisName);
        }
        if(chassisFactory!=null&&!"".equals(chassisFactory)){
            queryWrapper.like("chassis_factory",chassisFactory);
        }
        if(chassisType!=null&&!"".equals(chassisType)){
            queryWrapper.like("chassis_type",chassisType);
        }
        return Result.success(chassisService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Chassis> chassisList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Chassis chassis=new Chassis();
            chassis.setChassisName((String)row.get(0));
            chassis.setChassisPrice((String)row.get(1));
            chassis.setChassisFactory((String)row.get(2));
            chassis.setChassisType((String)row.get(3));
            chassis.setChassisImg((String)row.get(4));
            chassis.setChassisWater((String)row.get(5));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = new Date();
                chassis.setChassisTime(sdf.parse(sdf.format(date)));
            }catch (Exception e){
                e.printStackTrace();
            }
            chassisList.add(chassis);
        }
        chassisService.saveBatch(chassisList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Chassis> list = chassisService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("chassis_name","机箱名称");
        writer.addHeaderAlias("chassis_price","机箱价格");
        writer.addHeaderAlias("chassis_factory","机箱厂商");
        writer.addHeaderAlias("chassis_type","机箱类型");
        writer.addHeaderAlias("chassis_img","机箱图片");
        writer.addHeaderAlias("chassis_water","机箱水冷");


        writer.write(list,true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("机箱信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        writer.close();
        out.close();
    }

}

