package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.Cpu;
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

import com.yuhua.computerassemblyplatform.service.IPlatformService;
import com.yuhua.computerassemblyplatform.entity.Platform;




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
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private IPlatformService platformService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Platform platform){
        return Result.success(platformService.saveOrUpdate(platform));
    }

    //查询所有
    @GetMapping
    public List<Platform> findAll() {
        return platformService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(platformService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(platformService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String platformName,@RequestParam(defaultValue = "") String platformFactory,@RequestParam(defaultValue = "") String platformSize,@RequestParam(defaultValue = "") String platformResolution,@RequestParam(defaultValue = "") String platformRefreshRate,@RequestParam(defaultValue = "") String platformPort){
        QueryWrapper<Platform> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(platformName!=null&&!platformName.equals("")){
            queryWrapper.like("platform_name",platformName);
        }
        if(platformFactory!=null&&!platformFactory.equals("")){
            queryWrapper.like("platform_factory",platformFactory);
        }
        if(platformSize!=null&&!platformSize.equals("")){
            queryWrapper.like("platform_size",platformSize);
        }
        if(platformResolution!=null&&!platformResolution.equals("")){
            queryWrapper.like("platform_resolution",platformResolution);
        }
        if(platformRefreshRate!=null&&!platformRefreshRate.equals("")){
            queryWrapper.like("platform_refresh_rate",platformRefreshRate);
        }
        if(platformPort!=null&&!platformPort.equals("")){
            queryWrapper.like("platform_port",platformPort);
        }
        return Result.success(platformService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException {
        List<Platform> list = platformService.list();

        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("platformName","显示器名称");
        writer.addHeaderAlias("platformFactory","显示器厂商");
        writer.addHeaderAlias("platformSize","显示器尺寸");
        writer.addHeaderAlias("platformResolution","显示器分辨率");
        writer.addHeaderAlias("platformRefreshRate","显示器刷新率");
        writer.addHeaderAlias("platformPort","显示器接口");
        writer.addHeaderAlias("platformPrice","显示器价格");
        writer.addHeaderAlias("platformImg","显示器图片");


        writer.write(list, true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode("显示器信息表", "UTF-8");
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
        List<Platform> platformList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Platform platform=new Platform();
            platform.setPlatformName(row.get(1).toString());
            platform.setPlatformFactory(row.get(2).toString());
            platform.setPlatformSize(row.get(3).toString());
            platform.setPlatformResolution(row.get(4).toString());
            platform.setPlatformRefreshRate(row.get(5).toString());
            platform.setPlatformPort(row.get(6).toString());
            platform.setPlatformPrice(row.get(7).toString());
            platform.setPlatformImg(row.get(8).toString());
            platformList.add(platform);
        }
        platformService.saveBatch(platformList);
        return true;
    }

}

