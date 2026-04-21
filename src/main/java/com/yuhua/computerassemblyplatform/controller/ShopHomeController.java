package com.yuhua.computerassemblyplatform.controller;

import com.yuhua.computerassemblyplatform.common.Result;
import com.yuhua.computerassemblyplatform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopHome")
public class ShopHomeController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGraphicsService graphicsService;
    @Autowired
    private ICoolingService coolingService;
    @Autowired
    private IMainboardService mainboardService;
    @Autowired
    private IKeyboardService keyboardService;
    @Autowired
    private IMemorysticksService memorysticksService;
    @Autowired
    private IPowerService powerService;
    @Autowired
    private IHarddiskService harddiskService;
    @Autowired
    private ICpuService cpuService;
    @Autowired
    private IAudioService audioService;
    @Autowired
    private IChassisService chassisService;
    @Autowired
    private IPlatformService platformService;
    @Autowired
    private IMouseService mouseService;

    @GetMapping("/list")
    public Result get(){
        String imgSrc[] = new String[12];
        imgSrc[0]=graphicsService.findLast();
        imgSrc[1]=coolingService.findLast();
        imgSrc[2]=mainboardService.findLast();
        imgSrc[3]=keyboardService.findLast();
        imgSrc[4]=memorysticksService.findLast();
        imgSrc[5]=powerService.findLast();
        imgSrc[6]=harddiskService.findLast();
        imgSrc[7]=cpuService.findLast();
        imgSrc[8]=audioService.findLast();
        imgSrc[9]=chassisService.findLast();
        imgSrc[10]=platformService.findLast();
        imgSrc[11]=mouseService.findLast();
        return Result.success(imgSrc);
    }

}
