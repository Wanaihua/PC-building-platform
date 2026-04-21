<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="mouseName"></el-input>
      <el-input style="width: 200px" placeholder="请输入品牌" suffix-icon="el-icon-message" v-model="mouseFactory" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入类型" suffix-icon="el-icon-message" v-model="mouseType" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="query">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
    </div>


    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="mouseName" label="产品名" width="400">
      </el-table-column>
      <el-table-column prop="mouseImg" label="图片" width="120">
        <template slot-scope="scope">
          <img :src="scope.row.mouseImg" style="width: 90px;height: 90px">
        </template>
      </el-table-column>
      <el-table-column prop="mouseFactory" label="品牌" width="150">
      </el-table-column>
      <el-table-column prop="mouseType" label="类型" width="150">
      </el-table-column>
      <el-table-column prop="mousePrice" label="价格" sortable>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleInsert(scope.row.id)" :id=scope.row.id>选中<i v-if="scope.row.id==checkId" class="el-icon-check"></i> </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
export default {
  name: "Graphics",
  data() {
    return {
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: "headerBg",
      pageNum: 1,
      pageSize: 5,
      mouseName: "",
      mouseFactory: "",
      mouseType:"",
      checkId:sessionStorage.getItem("mouseId")==null?0:sessionStorage.getItem("mouseId"),
      total: 0,
      dialogFormVisible: false,
      multipleSelection: [],
      tableData: [],
      nowTime: "",
      form:{},
      roles:[]
    }
  },
  created() {
    //分页查询
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
      this.request.get("/mouse/page?",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          mouseName:this.mouseName,
          mouseType:this.mouseType,
          mouseFactory:this.mouseFactory
        }}).then(res=>{
        this.tableData = res.data.records;
        this.total=res.data.total;
      })
      this.request.get("/role").then(res=>{
        this.roles=res.data;
      })
    },
    reset() {
      this.mouseName = "";
      this.mouseType = "";
      this.mouseFactory = "";
      this.query();
    },
    handleAdd(){
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleInsert(id){
      this.$confirm('此操作将选中该商品并替换, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sessionStorage.setItem("mouseId",id);
        var button = document.getElementById(id);
        if(this.checkId!=0){
          var button1 = document.getElementById(this.checkId);
          button1.disabled = false;
        }
        button.disabled = true;
        this.checkId = id;
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消添加'
        });
      });
    },
    handleEdit(row){
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleAvatarSuccess(res){
      this.form.mouseImg=res;
    },
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
