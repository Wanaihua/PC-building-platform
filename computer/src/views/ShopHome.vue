<template>
  <div>
    <el-carousel :interval="4000" type="card" height="600px">
      <el-carousel-item v-for="item in 12" :key="item">
        <h3 class="medium">
          <img src="" :id="item" class="img">
        </h3>
      </el-carousel-item>
    </el-carousel>
    <el-descriptions title="联系/加入" style="margin-top: 100px;font-size: large">
      <el-descriptions-item label="管理员">YuHua</el-descriptions-item>
      <el-descriptions-item label="手机号">17779161007</el-descriptions-item>
      <el-descriptions-item label="邮箱">3062635305@qq.com</el-descriptions-item>
      <el-descriptions-item label="居住地">吉林省长春市</el-descriptions-item>
      <el-descriptions-item label="备注">欢迎使用快速装机平台, 本平台为个人开发,如有问题请联系我</el-descriptions-item>
      <el-descriptions-item label="联系地址">吉林省长春市高新北区北远达大街3000号</el-descriptions-item>
    </el-descriptions>
  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "ShopHome",
  data() {
    return {
      srcS:[],
      request: request
    }
  },
  mounted() {
    this.request.get('/shopHome/list').then(res => {
      if (res.code === '200') {
        this.srcS=res.data;
        require.ensure([], () => {
          for (let i = 0; i < this.srcS.length; i++) {
            const imaName=this.srcS[i].split('/')[this.srcS[i].split('/').length-1];
            const img = require(`@/assets/images/${imaName}`);
            document.getElementById(i+1).src=img;
          }
        })
      }
    })
  },
}

</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
.img{
  position: absolute;
  top: 50%;
  left: 50%;
  display: block;
  min-width: 100%;
  min-height: 100%;
  transform:translate(-50%,-50%);
}
</style>