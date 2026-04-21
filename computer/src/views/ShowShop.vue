<template>
  <div>

    <div style="padding: 10px 0">
      <el-upload
          action="http://localhost:8090/shopcar/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block"> <!--上传地址-->
        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i> </el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i> </el-button>
      <el-button type="primary" @click="save" class="ml-5">保存<i class="el-icon-check"></i> </el-button>
      <el-button type="primary" @click="clear" class="ml-5">清空<i class="el-icon-coffee"></i> </el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange" height="800"  show-summary :summary-method="getSummaries">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="shopName" label="产品名" width="1100">
      </el-table-column>
      <el-table-column prop="shopImg" label="图片" width="200">
        <template slot-scope="scope">
          <img :src="scope.row.shopImg" style="width: 90px;height: 90px">
        </template>
      </el-table-column>
      <el-table-column prop="shopFactory" label="品牌" width="150">
      </el-table-column>
      <el-table-column prop="shopPrice" label="价格" sortable width="150">
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Graphics",
  data() {
    return {
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: "headerBg",
      userId: "",
      pageNum: 1,
      pageSize: 5,
      mouseName: "",
      mouseFactory: "",
      mouseType:"",
      total: 0,
      dialogFormVisible: false,
      multipleSelection: [],
      tableData: [],
      nowTime: "",
      form:{},
      roles:[],
      shopJson:""
    }
  },
  created() {
    const userDate = localStorage.getItem("user");
    this.userId = JSON.parse(userDate).id;
    //分页查询
    const map = new Map();
    if(sessionStorage.getItem("mainBoardId")!=null) map.set("mainBoardId",sessionStorage.getItem("mainBoardId"));
    if(sessionStorage.getItem("graphicsId")!=null) map.set("graphicsId",sessionStorage.getItem("graphicsId"));
    if(sessionStorage.getItem("cpuId")!=null) map.set("cpuId",sessionStorage.getItem("cpuId"));
    if(sessionStorage.getItem("memorySticksId")!=null) map.set("memorySticksId",sessionStorage.getItem("memorySticksId"));
    if(sessionStorage.getItem("hardDiskId")!=null) map.set("hardDiskId",sessionStorage.getItem("hardDiskId"));
    if(sessionStorage.getItem("powerId")!=null) map.set("powerId",sessionStorage.getItem("powerId"));
    if(sessionStorage.getItem("coolingId")!=null) map.set("coolingId",sessionStorage.getItem("coolingId"));
    if(sessionStorage.getItem("platFormId")!=null) map.set("platFormId",sessionStorage.getItem("platFormId"));
    if(sessionStorage.getItem("chassisId")!=null) map.set("chassisId",sessionStorage.getItem("chassisId"));
    if(sessionStorage.getItem("audioId")!=null) map.set("audioId",sessionStorage.getItem("audioId"));
    if(sessionStorage.getItem("mouseId")!=null) map.set("mouseId",sessionStorage.getItem("mouseId"));
    if(sessionStorage.getItem("keyboardId")!=null) map.set("keyboardId",sessionStorage.getItem("keyboardId"));
    map.set("userId",this.userId+"");
    const myObj = Object.fromEntries(map);
    this.shopJson=JSON.stringify(myObj);
    console.log(this.shopJson);

    this.query();
  },
  methods:{
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize=val;
      this.pageNum=1;
      this.query();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val;
      this.query();
    },
    query(){
      this.request.post("/shopcar/page", this.shopJson).then(res => {
        this.tableData = res.data;
      });
      this.request.get("/role").then(res=>{
        this.roles=res.data;
      })
    },
    handleEdit(row){
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleSelectionChange(val){
      console.log(val);
      this.multipleSelection = val;
    },
    exp(){
      this.$confirm('此操作将导出装机配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        window.open("http://localhost:8090/shopcar/export");
        this.$message({
          message: '导出成功',
          type: 'success'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消导出'
        });
      });
    },
    handleExcelImportSuccess(){
      this.$message({
        message: '导入成功',
        type: 'success'
      });
      this.query();
    },
    handleAvatarSuccess(res){
      this.form.mouseImg=res;
    },
    save(){
      this.$confirm('检测到主板与CPU不兼容,请更换主板或者CPU,是否继续下单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //Json post给后台
        this.request.post("/shopcar/save",this.shopJson).then(res=>{
          this.$message({
            message: '保存成功',
            type: 'success'
          });
          this.clear();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消保存'
        });
      });
    },
    clear(){
      sessionStorage.clear();
      this.shopJson="";
      this.tableData=[];
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          console.log(index);          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' 元';
        } else {
          sums[index] = '';
        }
      });
      return sums;
    }
  }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
.el-menu-vertical-demo:not(.el-menu--collapse){
  width: 200px;
  height: 100%;
}
.el-menu-vertical-demo{
  transition: width 1s;
}
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload{
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover{
  border-color: #409EFF;
}
.avatar-uploader-icon{
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar{
  width: 138px;
  height: 138px;
  display: block;
}
</style>
