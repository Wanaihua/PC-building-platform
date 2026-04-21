package com.yuhua.computerassemblyplatform.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuhua.computerassemblyplatform.entity.MyShopCar;
import com.yuhua.computerassemblyplatform.entity.Shopcar;
import com.yuhua.computerassemblyplatform.entity.ShowShop;
import com.yuhua.computerassemblyplatform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import com.yuhua.computerassemblyplatform.common.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yuhua.computerassemblyplatform.entity.Order;




import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YuHua
 * @since 2024-01-14
 */
@RestController
@RequestMapping("/order")
public class OrderController {

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
    @Autowired
    private IOrderService orderService;


    //新增或更新
    @PostMapping
    public Result save(@RequestBody Order order){
        return Result.success(orderService.saveOrUpdate(order));
    }

    //查询所有
    @GetMapping
    public Result findAll() {
        List<Order> shopcar=orderService.list();
        List<MyShopCar> myShopCarList = new ArrayList<>();
        for (Order shopcar1 : shopcar) {
                double price = 0;
                MyShopCar myShopCar = new MyShopCar();
                myShopCar.setId(shopcar1.getId());
                myShopCar.setUserName(userService.getById(shopcar1.getUserId()).getUsername());
                myShopCar.setPhone(userService.getById(shopcar1.getUserId()).getPhone());
                myShopCar.setAddress(userService.getById(shopcar1.getUserId()).getAddress());
                myShopCar.setCreateTime(shopcar1.getCreateTime());
                myShopCar.setState(shopcar1.getState());
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
    //根据id删除
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Order order = orderService.getById(id);
        order.setState("已退款");
        orderService.saveOrUpdate(order);
        return Result.success();
    }

    //根据id申请退款
    @DeleteMapping("/{id}")
    public Result Delete(@PathVariable Integer id){
        Order order = orderService.getById(id);
        order.setState("申请退款");
        orderService.saveOrUpdate(order);
        return Result.success();
    }


    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(orderService.removeByIds(ids));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(orderService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
    //根据id查询添加
    @PostMapping("/{id}")
    public Result addById(@PathVariable Integer id){
        Shopcar shopcar = shopcarService.getById(id);
        Order order = new Order();
        double price = 0;
        order.setUserId(shopcar.getUserId());
        if(shopcar.getAudioId()!=null){
            order.setAudioId(shopcar.getAudioId());
            price += Double.parseDouble(audioService.getById(shopcar.getAudioId()).getAudioPrice());
        }
        if(shopcar.getChassisId()!=null){
            order.setChassisId(shopcar.getChassisId());
            price += Double.parseDouble(chassisService.getById(shopcar.getChassisId()).getChassisPrice());
        }
        if(shopcar.getCoolingId()!=null){
            order.setCoolingId(shopcar.getCoolingId());
            price += Double.parseDouble(coolingService.getById(shopcar.getCoolingId()).getCoolingPrice());
        }
        if(shopcar.getCpuId()!=null){
            order.setCpuId(shopcar.getCpuId());
            price += Double.parseDouble(cpuService.getById(shopcar.getCpuId()).getCpuPrice());
        }
        if(shopcar.getGraphicsId()!=null){
            order.setGraphicsId(shopcar.getGraphicsId());
            price += Double.parseDouble(graphicsService.getById(shopcar.getGraphicsId()).getGraphicsPrice());
        }
        if(shopcar.getHardDiskId()!=null){
            order.setHardDiskId(shopcar.getHardDiskId());
            price += Double.parseDouble(harddiskService.getById(shopcar.getHardDiskId()).getHarddiskPrice());
        }
        if(shopcar.getKeyboardId()!=null){
            order.setKeyboardId(shopcar.getKeyboardId());
            price += Double.parseDouble(keyboardService.getById(shopcar.getKeyboardId()).getKeyboardPrice());
        }
        if(shopcar.getMainboardId()!=null){
            order.setMainboardId(shopcar.getMainboardId());
            price += Double.parseDouble(mainboardService.getById(shopcar.getMainboardId()).getMainboardPrice());
        }
        if(shopcar.getMemorySticksId()!=null){
            order.setMemorySticksId(shopcar.getMemorySticksId());
            price += Double.parseDouble(memorysticksService.getById(shopcar.getMemorySticksId()).getMemorySticksPrice());
        }
        if(shopcar.getMouseId()!=null){
            order.setMouseId(shopcar.getMouseId());
            price += Double.parseDouble(mouseService.getById(shopcar.getMouseId()).getMousePrice());
        }
        if(shopcar.getPlatformId()!=null){
            order.setPlatformId(shopcar.getPlatformId());
            price += Double.parseDouble(platformService.getById(shopcar.getPlatformId()).getPlatformPrice());
        }
        if(shopcar.getPowerId()!=null){
            order.setPowerId(shopcar.getPowerId());
            price += Double.parseDouble(powerService.getById(shopcar.getPowerId()).getPowerPrice());
        }
        order.setState("已付款");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        order.setCreateTime(sdf.format(System.currentTimeMillis()));
        order.setTotalPrice(price);
        return Result.success(orderService.save(order));
    }
    @GetMapping("/myOrder")
    public Result myShopCar(@RequestParam("userId") int userId) {
        List<Order> shopcar = orderService.getByUserId(userId);
        List<MyShopCar> myShopCarList = new ArrayList<>();
        for (Order shopcar1 : shopcar) {
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
            myShopCar.setState(shopcar1.getState());
            myShopCar.setTotalPrice(price);
            myShopCarList.add(myShopCar);
        }
        return Result.success(myShopCarList);
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id){
        Order shopcar = orderService.getById(id);
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

