<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="platformName"></el-input>
      <el-input style="width: 200px" placeholder="请输入品牌" suffix-icon="el-icon-message" v-model="platformFactory" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入刷新率" suffix-icon="el-icon-message" v-model="platformRefreshRate" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入尺寸" suffix-icon="el-icon-message" v-model="platformSize" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入分辨率" suffix-icon="el-icon-message" v-model="platformResolution" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入接口" suffix-icon="el-icon-message" v-model="platformPort" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="query">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
      <el-button type="danger" @click="deleteBatch">批量删除<i class="el-icon-remove-outline"></i> </el-button>
      <el-upload
          action="http://localhost:8090/platform/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block"> <!--上传地址-->
        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i> </el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i> </el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection"  width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="platformName" label="产品名" width="450">
      </el-table-column>
      <el-table-column prop="platformImg" label="图片" width="120">
        <template slot-scope="scope">
          <img :src="scope.row.platformImg" style="width: 90px;height: 90px">
        </template>
      </el-table-column>
      <el-table-column prop="platformPrice" label="价格" sortable>
      </el-table-column>
      <el-table-column prop="platformFactory" label="品牌" width="150">
      </el-table-column>
      <el-table-column prop="platformSize" label="尺寸">
      </el-table-column>
      <el-table-column prop="platformResolution" label="分辨率" width="150" sortable>
      </el-table-column>
      <el-table-column prop="platformRefreshRate" label="刷新率" width="150" sortable>
      </el-table-column>
      <el-table-column prop="platformPort" label="接口">
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i> </el-button>
          <el-button type="text" size="small" @click="handleDelete(scope.row.id)">删除<i class="el-icon-remove-outline"></i> </el-button>
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

    <el-dialog title="显示器信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="form.platformImg" :src="form.platformImg" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item label="" style="display: none">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="显示器">
          <el-input v-model="form.platformName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌">
          <el-input v-model="form.platformFactory" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.platformPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="尺寸">
          <el-input v-model="form.platformSize" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分辨率">
          <el-input v-model="form.platformResolution" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="刷新率">
          <el-input v-model="form.platformRefreshRate" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="接口">
          <el-input v-model="form.platformPort" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save()">确 定</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
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
      platformSize: "",
      platformRefreshRate: "",
      platformFactory: "",
      platformName: "",
      platformPort: "",
      platformResolution: "",
      total: 0,
      dialogFormVisible: false,
      multipleSelection: [],
      tableData: [],
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
      this.request.get("/platform/page?",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          platformName:this.platformName,
          platformFactory:this.platformFactory,
          platformRefreshRate:this.platformRefreshRate,
          platformResolution:this.platformResolution,
          platformPort:this.platformPort,
          platformSize:this.platformSize
        }}).then(res=>{
        this.tableData = res.data.records;
        this.total=res.data.total;
      })
      this.request.get("/role").then(res=>{
        this.roles=res.data;
      })
    },
    reset() {
      this.platformName="";
      this.platformFactory="";
      this.platformRefreshRate="";
      this.platformResolution="";
      this.platformPort="";
      this.platformSize="";
      this.query();
    },
    handleAdd(){
      this.dialogFormVisible = true;
      this.form = {};
    },
    save(){
      this.dialogFormVisible = false;
      this.request.post("/platform",this.form).then(res=>{
        if(res.code==='200'){
          this.$message({
            message: '保存成功',
            type: 'success'
          });
          this.query();
        }else {
          this.$message.error('保存失败');
        }
      })
    },
    handleEdit(row){
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(id){
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/platform/"+id).then(res=>{
          if(res.code==='200'){
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.query();
          }else {
            this.$message.error('删除失败');
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val){
      console.log(val);
      this.multipleSelection = val;
    },
    deleteBatch() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '请至少选择一条数据',
          type: 'warning'
        });
        return;
      }
      this.$confirm('此操作将永久删除这些商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = [];
        this.multipleSelection.forEach(item => {
          ids.push(item.id);
        })
        console.log(ids);
        this.request.post("/platform/deleteBatch", ids).then(res => {
          if (res.code==='200') {
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.query();
          } else {
            this.$message.error('删除失败');
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    exp(){
      this.$confirm('此操作将导出显示器, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        window.open("http://localhost:8090/platform/export");
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
      this.form.platformImg=res;
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
