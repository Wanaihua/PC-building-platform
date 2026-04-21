<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="graphicsName"></el-input>
      <el-input style="width: 200px" placeholder="请输入品牌" suffix-icon="el-icon-message" v-model="graphicsFactory" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="query">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
      <el-button type="danger" @click="deleteBatch">批量删除<i class="el-icon-remove-outline"></i> </el-button>
      <el-upload
          action="http://localhost:8090/graphics/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block"> <!--上传地址-->
        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i> </el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i> </el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection"  width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="graphicsName" label="产品名" width="450">
      </el-table-column>
      <el-table-column prop="graphicsImg" label="图片" width="120">
        <template slot-scope="scope">
          <img :src="scope.row.graphicsImg" style="width: 90px;height: 90px">
        </template>
      </el-table-column>
      <el-table-column prop="graphicsPrice" label="价格" sortable>
      </el-table-column>
      <el-table-column prop="graphicsFactory" label="品牌" width="150">
      </el-table-column>
      <el-table-column prop="graphicsMemory" label="内存" width="150" sortable>
      </el-table-column>
      <el-table-column prop="graphicsBitWidth" label="带宽" width="150" sortable>
      </el-table-column>
      <el-table-column prop="graphicsFan" label="风扇">
      </el-table-column>
      <el-table-column prop="graphicsLight" label="灯光">
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

    <el-dialog title="显卡信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="form.graphicsImg" :src="form.graphicsImg" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item label="" style="display: none">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="显卡名">
          <el-input v-model="form.graphicsName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌">
          <el-input v-model="form.graphicsFactory" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.graphicsPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内存">
          <el-input v-model="form.graphicsMemory" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="带宽">
          <el-input v-model="form.graphicsBitWidth" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="风扇">
          <el-input v-model="form.graphicsFan" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="灯光">
          <el-input v-model="form.graphicsLight" autocomplete="off"></el-input>
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
      graphicsName: "",
      graphicsFactory: "",
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
      this.request.get("/graphics/page?",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          graphicsName:this.graphicsName,
          graphicsFactory:this.graphicsFactory,
        }}).then(res=>{
        this.tableData = res.data.records;
        this.total=res.data.total;
      })
      this.request.get("/role").then(res=>{
        this.roles=res.data;
      })
    },
    reset() {
      this.username = "";
      this.address = "";
      this.email = "";
      this.query();
    },
    handleAdd(){
      this.dialogFormVisible = true;
      this.form = {};
    },
    save(){
      this.dialogFormVisible = false;
      this.request.post("/graphics",this.form).then(res=>{
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
        this.request.delete("/graphics/"+id).then(res=>{
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
      this.$confirm('此操作将永久删除这些用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = [];
        this.multipleSelection.forEach(item => {
          ids.push(item.id);
        })
        console.log(ids);
        this.request.post("/graphics/deleteBatch", ids).then(res => {
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
      this.$confirm('此操作将导出显卡, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        window.open("http://localhost:8090/graphics/export");
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
      this.form.graphicsImg=res;
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
