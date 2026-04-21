package com.yuhua.computerassemblyplatform.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.yuhua.computerassemblyplatform.common.Result;
import com.yuhua.computerassemblyplatform.entity.Order;
import com.yuhua.computerassemblyplatform.entity.User;
import com.yuhua.computerassemblyplatform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

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



    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map=new HashMap<>();
        map.put("x", CollUtil.newArrayList("周一","周二","周三","周四","周五","周六","周日"));
        map.put("y",CollUtil.newArrayList(100,200,300,400,500,600,700));
        return Result.success(map);
    }

    @GetMapping("/userMembers")
    public Result getMembers(){
        List<User> list=userService.list();
        int q1=0;//第一季度
        int q2=0;//第二季度
        int q3=0;//第三季度
        int q4=0;//第四季度
        int allCount=list.size();
        for (User user : list) {
            Date createTime = user.getCreatTime();
            //今年内
            if(DateUtil.year(createTime)!=DateUtil.year(new Date())){
                continue;
            }
            //季度
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:
                    q1++;
                    break;
                case Q2:
                    q2++;
                    break;
                case Q3:
                    q3++;
                    break;
                case Q4:
                    q4++;
                    break;
                default:
                    break;
            }
        }
        List<List<Integer>> data=CollUtil.newArrayList();
        data.add(CollUtil.newArrayList(q1,q2,q3,q4));
        data.add(CollUtil.newArrayList(allCount));
        return Result.success(data);
    }

    @GetMapping("/shopMembers")
    public Result getShopMembers(){
        long q1=0;//显卡数量
        long q2=0;//主板数量
        long q3=0;//CPU数量
        long q4=0;//内存数量
        long q5=0;//硬盘数量
        long q6=0;//电源数量
        long q7=0;//机箱数量
        long q8=0;//散热器数量
        long q9=0;//显示器数量
        long q10=0;//键盘数量
        long q11=0;//鼠标数量
        long q12=0;//音箱数量

        q1=graphicsService.count();
        q2=mainboardService.count();
        q3=cpuService.count();
        q4=memorysticksService.count();
        q5=harddiskService.count();
        q6=powerService.count();
        q7=chassisService.count();
        q8=coolingService.count();
        q9=platformService.count();
        q10=keyboardService.count();
        q11=mouseService.count();
        q12=audioService.count();

        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12));

    }

    //获取销售总额
    @GetMapping("/totalPrice")
    public Result getTotalPrice(){
        double totalPrice=0;
        List<Order> list=orderService.list();
        for (Order order : list) {
            totalPrice+=order.getTotalPrice();
        }
        return Result.success(totalPrice);
    }

    //获取订单总数
    @GetMapping("/orderCount")
    public Result getOrderCount(){
        return Result.success(orderService.count());
    }
}
