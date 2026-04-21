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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yuhua.computerassemblyplatform.service.IAudioService;
import com.yuhua.computerassemblyplatform.entity.Audio;




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
@RequestMapping("/audio")
public class AudioController {

    @Autowired
    private IAudioService audioService;

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Audio audio){
        return Result.success(audioService.saveOrUpdate(audio));
    }

    //查询所有
    @GetMapping
    public List<Audio> findAll() {
        return audioService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(audioService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(audioService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String audioName,@RequestParam(defaultValue = "") String audioFactory,@RequestParam(defaultValue = "") String audioType){
        QueryWrapper<Audio> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(audioName!=null&&!"".equals(audioName)){
            queryWrapper.like("audio_name",audioName);
        }
        if(audioFactory!=null&&!"".equals(audioFactory)){
            queryWrapper.like("audio_factory",audioFactory);
        }
        if(audioType!=null&&!"".equals(audioType)){
            queryWrapper.like("audio_type",audioType);
        }
        return Result.success(audioService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    //导入
    @PostMapping("/import")
    public Boolean importExcel(@RequestParam("file") MultipartFile file)throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> list=reader.read(1);
        List<Audio> audioList= CollUtil.newArrayList();
        for(List<Object> row:list){
            Audio audio=new Audio();
            audio.setAudioName((String) row.get(0));
            audio.setAudioPrice((String) row.get(1));
            audio.setAudioFactory((String) row.get(2));
            audio.setAudioType((String) row.get(3));
            audio.setAudioImg((String) row.get(4));

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = new Date();
                audio.setAudioTime(sdf.parse(sdf.format(date)));
            }catch (Exception e){
                e.printStackTrace();
            }
            audioList.add(audio);
        }
        audioService.saveBatch(audioList);
        return true;
    }


    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws IOException{
        List<Audio> list = audioService.list();

        ExcelWriter writer= ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("audioName","音箱名称");
        writer.addHeaderAlias("audioPrice","音箱价格");
        writer.addHeaderAlias("audioFactory","音箱厂商");
        writer.addHeaderAlias("audioType","音箱类型");
        writer.addHeaderAlias("audioImg","音箱图片");




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

