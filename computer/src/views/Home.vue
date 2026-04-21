<template>
  <div>
    <el-row :gutter="10" style="margin-buttom:40px">
      <el-col :span="6">
        <el-card  style="color: #409EFF">
          <div><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{userCount}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money"></i>销售总量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{totalPrice}} 元
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card"></i>订单总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{orderCount}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop"></i>商品总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{shopCount}}
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row style="margin-top: 50px;">
      <el-col :span="12">
        <div id="main" style="width: 500px;height: 450px;margin-left: 200px;"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px;height: 400px;margin-left: 100px;"></div>
      </el-col>
    </el-row>



  </div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";
export default {
  name: "Home",
  data(){

    return{
      userCount:0,
      shopCount:0,
      totalPrice:0,
      orderCount:0,
    }
  },
  mounted() {  //页面元素加载完成后执行

    var option,pie;
    //柱状图
    option = {
      title: {
        text: '各季度用户注册数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name:'用户',
          data: [],
          type: 'bar'
        },
        {
          name:'用户',
          data: [],
          type: 'line'
        }
      ]
    };

    //饼图
    pie = {
      title: {
        text: '各商品数量统计',
        subtext: '比例图',
        left: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name:"",
          type: 'pie',
          radius: '70%',
          center: ['55%', '60%'],
          label:{
            type:'pie',
            radius:'60%',
            label:{             //饼图图形上的文本标签
              normal:{
                show:true,
                position:'inner',  //标签的位置
                textStyle:{
                  fontWeight:300,
                  fontSize:16,       //文字的字体大小
                  color:'#fff',
                },
                formatter:'{b}:{d}%'
              }
            }
          },
          data: [],
        }
      ]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/echarts/userMembers").then(res =>{
      //填空
      //  option.xAxis.data=res.data.x;
      option.series[0].data=res.data[0];
      option.series[1].data=res.data[0];
      //数据准备完毕，开始绘制图表
      myChart.setOption(option);

      this.userCount=res.data[1][0];
    })

    this.request.get("/echarts/shopMembers").then(res => {
      pie.series[0].data = [
        {name:'显卡',value:res.data[0]},
        {name:'主板',value:res.data[1]},
        {name:'CPU',value:res.data[2]},
        {name:'内存',value:res.data[3]},
        {name:'硬盘',value:res.data[4]},
        {name:'电源',value:res.data[5]},
        {name:'机箱',value:res.data[6]},
        {name:'散热器',value:res.data[7]},
        {name:'显示器',value:res.data[8]},
        {name:'键盘',value:res.data[9]},
        {name:'鼠标',value:res.data[10]},
        {name:'电源',value:res.data[11]},
      ];
      pieChart.setOption(pie);
      this.shopCount=res.data[0]+res.data[1]+res.data[2]+res.data[3]+res.data[4]+res.data[5]+res.data[6]+res.data[7]+res.data[8]+res.data[9]+res.data[10]+res.data[11];
    })
    pie && pieChart.setOption(pie);

    this.request.get("/echarts/totalPrice").then(res => {
      this.totalPrice=res.data;
    })

    this.request.get("/echarts/orderCount").then(res => {
      this.orderCount=res.data;
    })

  },

}
</script>

<style scoped>

</style>