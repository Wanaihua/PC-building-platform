package com.yuhua.computerassemblyplatform.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.*;
import com.yuhua.computerassemblyplatform.service.*;
import io.swagger.models.auth.In;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuhua.computerassemblyplatform.common.Result;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YuHua
 * @since 2024-01-06
 */
@RestController
@RequestMapping("/shopcar")
public class ShopcarController {

    @Autowired
    private IShopcarService shopcarService;
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

    //新增或更新
    @PostMapping
    public Result save(@RequestBody Shopcar shopcar){
        return Result.success(shopcarService.saveOrUpdate(shopcar));
    }

    //查询所有
    @GetMapping
    public List<Shopcar> findAll() {
        return shopcarService.list();
    }

    //根据id删除
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        return Result.success(shopcarService.removeById(id));
    }

    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(shopcarService.removeByIds(ids));
    }

    //分页查询
    @PostMapping("/page")
    public Result findPage(@RequestBody String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        List<ShowShop> showShopList = new ArrayList<>();
        ShowShop showShop;
        Map<String,String> map = jsonObject.toBean(Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            Integer id = Integer.parseInt(entry.getValue());
            switch (name){
                case "graphicsId":
                    Graphics graphics = graphicsService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(graphics.getGraphicsFactory());
                    showShop.setShopName(graphics.getGraphicsName());
                    showShop.setShopPrice(graphics.getGraphicsPrice());
                    showShop.setShopImg(graphics.getGraphicsImg());
                    showShopList.add(showShop);
                    break;
                case "coolingId":
                    Cooling cooling = coolingService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(cooling.getCoolingFactory());
                    showShop.setShopName(cooling.getCoolingName());
                    showShop.setShopPrice(cooling.getCoolingPrice());
                    showShop.setShopImg(cooling.getCoolingImg());
                    showShopList.add(showShop);
                    break;
                case "mainBoardId":
                    Mainboard mainboard = mainboardService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(mainboard.getMainboardFactory());
                    showShop.setShopName(mainboard.getMainboardName());
                    showShop.setShopPrice(mainboard.getMainboardPrice());
                    showShop.setShopImg(mainboard.getMainboardImg());
                    showShopList.add(showShop);
                    break;
                case "keyboardId":
                    Keyboard keyboard = keyboardService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(keyboard.getKeyboardFactory());
                    showShop.setShopName(keyboard.getKeyboardName());
                    showShop.setShopPrice(keyboard.getKeyboardPrice());
                    showShop.setShopImg(keyboard.getKeyboardImg());
                    showShopList.add(showShop);
                    break;
                case "memorySticksId":
                    Memorysticks memorysticks = memorysticksService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(memorysticks.getMemorySticksFactory());
                    showShop.setShopName(memorysticks.getMemorySticksName());
                    showShop.setShopPrice(memorysticks.getMemorySticksPrice());
                    showShop.setShopImg(memorysticks.getMemorySticksImg());
                    showShopList.add(showShop);
                    break;
                case "powerId":
                    Power power = powerService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(power.getPowerFactory());
                    showShop.setShopName(power.getPowerName());
                    showShop.setShopPrice(power.getPowerPrice());
                    showShop.setShopImg(power.getPowerImg());
                    showShopList.add(showShop);
                    break;
                case "hardDiskId":
                    Harddisk harddisk = harddiskService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(harddisk.getHarddiskFactory());
                    showShop.setShopName(harddisk.getHarddiskName());
                    showShop.setShopPrice(harddisk.getHarddiskPrice());
                    showShop.setShopImg(harddisk.getHarddiskImg());
                    showShopList.add(showShop);
                    break;
                case "cpuId":
                    Cpu cpu = cpuService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(cpu.getCpuFactory());
                    showShop.setShopName(cpu.getCpuName());
                    showShop.setShopPrice(cpu.getCpuPrice());
                    showShop.setShopImg(cpu.getCpuImg());
                    showShopList.add(showShop);
                    break;
                case "audioId":
                    Audio audio = audioService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(audio.getAudioFactory());
                    showShop.setShopName(audio.getAudioName());
                    showShop.setShopPrice(audio.getAudioPrice());
                    showShop.setShopImg(audio.getAudioImg());
                    showShopList.add(showShop);
                    break;
                case "chassisId":
                    Chassis chassis = chassisService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(chassis.getChassisFactory());
                    showShop.setShopName(chassis.getChassisName());
                    showShop.setShopPrice(chassis.getChassisPrice());
                    showShop.setShopImg(chassis.getChassisImg());
                    showShopList.add(showShop);
                    break;
                case "platFormId":
                    Platform platform = platformService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(platform.getPlatformFactory());
                    showShop.setShopName(platform.getPlatformName());
                    showShop.setShopPrice(platform.getPlatformPrice());
                    showShop.setShopImg(platform.getPlatformImg());
                    showShopList.add(showShop);
                    break;
                case "mouseId":
                    Mouse mouse = mouseService.getById(id);
                    showShop = new ShowShop();
                    showShop.setId(showShopList.size()+1);
                    showShop.setShopFactory(mouse.getMouseFactory());
                    showShop.setShopName(mouse.getMouseName());
                    showShop.setShopPrice(mouse.getMousePrice());
                    showShop.setShopImg(mouse.getMouseImg());
                    showShopList.add(showShop);
                    break;

            }
        }
        return Result.success(showShopList);
    }
    //接受表单数据
    @PostMapping("/save")
    public Result save(@RequestBody String json){
        Shopcar shopcar = new Shopcar();
        JSONObject jsonObject = new JSONObject(json);
        Shopcar shop=new Shopcar();
        int userId = jsonObject.getInt("userId");
        shop.setUserId(userId);
        shop.setCoolingId(jsonObject.getInt("coolingId"));
        shop.setGraphicsId(jsonObject.getInt("graphicsId"));
        shop.setChassisId(jsonObject.getInt("chassisId"));
        shop.setAudioId(jsonObject.getInt("audioId"));
        shop.setCpuId(jsonObject.getInt("cpuId"));
        shop.setHardDiskId(jsonObject.getInt("hardDiskId"));
        shop.setKeyboardId(jsonObject.getInt("keyboardId"));
        shop.setMainboardId(jsonObject.getInt("mainBoardId"));
        shop.setMemorySticksId(jsonObject.getInt("memorySticksId"));
        shop.setMouseId(jsonObject.getInt("mouseId"));
        shop.setPlatformId(jsonObject.getInt("platFormId"));
        shop.setPowerId(jsonObject.getInt("powerId"));
        shopcarService.save(shop);
        return Result.success(shopcar);
    }
    //我的配置
    @GetMapping("/myShopCar")
    public Result myShopCar(@RequestParam("userId") int userId) {
        List<Shopcar> shopcar = shopcarService.getByUserId(userId);
        List<MyShopCar> myShopCarList = new ArrayList<>();
        for (Shopcar shopcar1 : shopcar) {
            double price = 0;
            MyShopCar myShopCar = new MyShopCar();
            myShopCar.setId(shopcar1.getId());
            if(shopcar1.getAudioId()!=null){
                myShopCar.setAudioName(audioService.getById(shopcar1.getAudioId()).getAudioName());
                price += Double.parseDouble(audioService.getById(shopcar1.getAudioId()).getAudioPrice());
            }
            if(shopcar1.getChassisId()!=null){
                myShopCar.setChassicsName(chassisService.getById(shopcar1.getChassisId()).getChassisName());
                price += Double.parseDouble(chassisService.getById(shopcar1.getChassisId()).getChassisPrice());
            }
            if(shopcar1.getCoolingId()!=null) {
                myShopCar.setCoolingName(coolingService.getById(shopcar1.getCoolingId()).getCoolingName());
                price += Double.parseDouble(coolingService.getById(shopcar1.getCoolingId()).getCoolingPrice());
            }
            if(shopcar1.getCpuId()!=null) {
                myShopCar.setCpuName(cpuService.getById(shopcar1.getCpuId()).getCpuName());
                price += Double.parseDouble(cpuService.getById(shopcar1.getCpuId()).getCpuPrice());
            }
            if(shopcar1.getGraphicsId()!=null) {
                myShopCar.setGraphicsName(graphicsService.getById(shopcar1.getGraphicsId()).getGraphicsName());
                price += Double.parseDouble(graphicsService.getById(shopcar1.getGraphicsId()).getGraphicsPrice());
            }
            if(shopcar1.getHardDiskId()!=null) {
                myShopCar.setHarddiskName(harddiskService.getById(shopcar1.getHardDiskId()).getHarddiskName());
                price += Double.parseDouble(harddiskService.getById(shopcar1.getHardDiskId()).getHarddiskPrice());
            }
            if(shopcar1.getKeyboardId()!=null) {
                myShopCar.setKeyboardName(keyboardService.getById(shopcar1.getKeyboardId()).getKeyboardName());
                price += Double.parseDouble(keyboardService.getById(shopcar1.getKeyboardId()).getKeyboardPrice());
            }
            if(shopcar1.getMainboardId()!=null) {
                myShopCar.setMainboardName(mainboardService.getById(shopcar1.getMainboardId()).getMainboardName());
                price += Double.parseDouble(mainboardService.getById(shopcar1.getMainboardId()).getMainboardPrice());
            }
            if(shopcar1.getMemorySticksId()!=null) {
                myShopCar.setMemorySticksName(memorysticksService.getById(shopcar1.getMemorySticksId()).getMemorySticksName());
                price += Double.parseDouble(memorysticksService.getById(shopcar1.getMemorySticksId()).getMemorySticksPrice());
            }
            if(shopcar1.getMouseId()!=null) {
                myShopCar.setMouseName(mouseService.getById(shopcar1.getMouseId()).getMouseName());
                price += Double.parseDouble(mouseService.getById(shopcar1.getMouseId()).getMousePrice());
            }
            if(shopcar1.getPlatformId()!=null) {
                myShopCar.setPlatformName(platformService.getById(shopcar1.getPlatformId()).getPlatformName());
                price += Double.parseDouble(platformService.getById(shopcar1.getPlatformId()).getPlatformPrice());
            }
            if(shopcar1.getPowerId()!=null) {
                myShopCar.setPowerName(powerService.getById(shopcar1.getPowerId()).getPowerName());
                price += Double.parseDouble(powerService.getById(shopcar1.getPowerId()).getPowerPrice());
            }
            myShopCar.setTotalPrice(price);
            myShopCarList.add(myShopCar);
        }
        return Result.success(myShopCarList);
    }
    //获取单一配置
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        Shopcar shopcar = shopcarService.getById(id);
        List<ShowShop> showShopList = new ArrayList<>();
        if(shopcar.getAudioId()!=null){
            //音响
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(audioService.getById(shopcar.getAudioId()).getAudioFactory());
            showShop.setShopImg(audioService.getById(shopcar.getAudioId()).getAudioImg());
            showShop.setShopName(audioService.getById(shopcar.getAudioId()).getAudioName());
            showShop.setShopPrice(audioService.getById(shopcar.getAudioId()).getAudioPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getChassisId()!=null){
            //机箱
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(chassisService.getById(shopcar.getChassisId()).getChassisFactory());
            showShop.setShopImg(chassisService.getById(shopcar.getChassisId()).getChassisImg());
            showShop.setShopName(chassisService.getById(shopcar.getChassisId()).getChassisName());
            showShop.setShopPrice(chassisService.getById(shopcar.getChassisId()).getChassisPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getCoolingId()!=null){
            //散热器
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(coolingService.getById(shopcar.getCoolingId()).getCoolingFactory());
            showShop.setShopImg(coolingService.getById(shopcar.getCoolingId()).getCoolingImg());
            showShop.setShopName(coolingService.getById(shopcar.getCoolingId()).getCoolingName());
            showShop.setShopPrice(coolingService.getById(shopcar.getCoolingId()).getCoolingPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getCpuId()!=null){
            //cpu
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(cpuService.getById(shopcar.getCpuId()).getCpuFactory());
            showShop.setShopImg(cpuService.getById(shopcar.getCpuId()).getCpuImg());
            showShop.setShopName(cpuService.getById(shopcar.getCpuId()).getCpuName());
            showShop.setShopPrice(cpuService.getById(shopcar.getCpuId()).getCpuPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getGraphicsId()!=null){
            //显卡
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(graphicsService.getById(shopcar.getGraphicsId()).getGraphicsFactory());
            showShop.setShopImg(graphicsService.getById(shopcar.getGraphicsId()).getGraphicsImg());
            showShop.setShopName(graphicsService.getById(shopcar.getGraphicsId()).getGraphicsName());
            showShop.setShopPrice(graphicsService.getById(shopcar.getGraphicsId()).getGraphicsPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getHardDiskId()!=null){
            //硬盘
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(harddiskService.getById(shopcar.getHardDiskId()).getHarddiskFactory());
            showShop.setShopImg(harddiskService.getById(shopcar.getHardDiskId()).getHarddiskImg());
            showShop.setShopName(harddiskService.getById(shopcar.getHardDiskId()).getHarddiskName());
            showShop.setShopPrice(harddiskService.getById(shopcar.getHardDiskId()).getHarddiskPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getKeyboardId()!=null){
            //键盘
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(keyboardService.getById(shopcar.getKeyboardId()).getKeyboardFactory());
            showShop.setShopImg(keyboardService.getById(shopcar.getKeyboardId()).getKeyboardImg());
            showShop.setShopName(keyboardService.getById(shopcar.getKeyboardId()).getKeyboardName());
            showShop.setShopPrice(keyboardService.getById(shopcar.getKeyboardId()).getKeyboardPrice());
            showShopList.add(showShop);
        }
        if (shopcar.getMainboardId()!=null){
            //主板
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(mainboardService.getById(shopcar.getMainboardId()).getMainboardFactory());
            showShop.setShopImg(mainboardService.getById(shopcar.getMainboardId()).getMainboardImg());
            showShop.setShopName(mainboardService.getById(shopcar.getMainboardId()).getMainboardName());
            showShop.setShopPrice(mainboardService.getById(shopcar.getMainboardId()).getMainboardPrice());
            showShopList.add(showShop);
        }
        if(shopcar.getMemorySticksId()!=null) {
            //内存条
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size() + 1);
            showShop.setShopFactory(memorysticksService.getById(shopcar.getMemorySticksId()).getMemorySticksFactory());
            showShop.setShopImg(memorysticksService.getById(shopcar.getMemorySticksId()).getMemorySticksImg());
            showShop.setShopName(memorysticksService.getById(shopcar.getMemorySticksId()).getMemorySticksName());
            showShop.setShopPrice(memorysticksService.getById(shopcar.getMemorySticksId()).getMemorySticksPrice());
            showShopList.add(showShop);
        }
        if(shopcar.getMouseId()!=null){
            //显示器
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(mouseService.getById(shopcar.getMouseId()).getMouseFactory());
            showShop.setShopImg(mouseService.getById(shopcar.getMouseId()).getMouseImg());
            showShop.setShopName(mouseService.getById(shopcar.getMouseId()).getMouseName());
            showShop.setShopPrice(mouseService.getById(shopcar.getMouseId()).getMousePrice());
            showShopList.add(showShop);
        }
        if(shopcar.getPowerId()!=null){
            //电源
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(powerService.getById(shopcar.getPowerId()).getPowerFactory());
            showShop.setShopImg(powerService.getById(shopcar.getPowerId()).getPowerImg());
            showShop.setShopName(powerService.getById(shopcar.getPowerId()).getPowerName());
            showShop.setShopPrice(powerService.getById(shopcar.getPowerId()).getPowerPrice());
            showShopList.add(showShop);
        }
        if(shopcar.getPlatformId()!=null){
            //显示器
            ShowShop showShop = new ShowShop();
            showShop.setId(showShopList.size()+1);
            showShop.setShopFactory(platformService.getById(shopcar.getPlatformId()).getPlatformFactory());
            showShop.setShopImg(platformService.getById(shopcar.getPlatformId()).getPlatformImg());
            showShop.setShopName(platformService.getById(shopcar.getPlatformId()).getPlatformName());
            showShop.setShopPrice(platformService.getById(shopcar.getPlatformId()).getPlatformPrice());
            showShopList.add(showShop);
        }

        return Result.success(showShopList);
    }
}

