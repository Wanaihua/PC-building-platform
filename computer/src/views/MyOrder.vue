<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="query">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i> </el-button>
      <el-button type="danger" @click="deleteBatch">批量删除<i class="el-icon-remove-outline"></i> </el-button>
      <el-upload
          action="http://localhost:8090/order/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block"> <!--上传地址-->
        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i> </el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i> </el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection"  width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" fixed>
      </el-table-column>
      <el-table-column prop="mainboardName" label="主板" width="200">
      </el-table-column>
      <el-table-column prop="cpuName" label="CPU" width="200">
      </el-table-column>
      <el-table-column prop="memorySticksName" label="内存" width="200">
      </el-table-column>
      <el-table-column prop="graphicsName" label="显卡" width="200">
      </el-table-column>
      <el-table-column prop="harddiskName" label="硬盘" width="200">
      </el-table-column>
      <el-table-column prop="coolingName" label="散热" width="200">
      </el-table-column>
      <el-table-column prop="powerName" label="电源" width="200">
      </el-table-column>
      <el-table-column prop="chassicsName" label="机箱" width="200">
      </el-table-column>
      <el-table-column prop="audioName" label="音响" width="200">
      </el-table-column>
      <el-table-column prop="platformName" label="显示器" width="200">
      </el-table-column>
      <el-table-column prop="keyboardName" label="键盘" width="200">
      </el-table-column>
      <el-table-column prop="mouseName" label="鼠标" width="200">
      </el-table-column>
      <el-table-column prop="state" label="状态" width="200">
      </el-table-column>
      <el-table-column prop="totalPrice" label="总价/元" width="120">
      </el-table-column>

      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="handleEdit(scope.row)">查看<i class="el-icon-edit"></i> </el-button>
          <el-button type="text" size="small" @click="handleDelete(scope.row.id)">退款<i class="el-icon-remove-outline"></i> </el-button>
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
    <el-dialog title="配置信息" :visible.sync="dialogFormVisible" width="70%">
      <el-table :data="oneTableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange" height="600"  show-summary :summary-method="getSummaries">
        <el-table-column prop="id" label="ID" width="80">
        </el-table-column>
        <el-table-column prop="shopName" label="产品名" width="600">
        </el-table-column>
        <el-table-column prop="shopImg" label="图片" width="300">
          <template slot-scope="scope">
            <img :src="scope.row.shopImg" style="width: 90px;height: 90px">
          </template>
        </el-table-column>
        <el-table-column prop="shopFactory" label="品牌" width="150">
        </el-table-column>
        <el-table-column prop="shopPrice" label="价格" sortable>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "ShopCar",
  data(){
    return{
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
      ModelId: "",
      total: 0,
      dialogFormVisible: false,
      multipleSelection: [],
      tableData: [],
      oneTableData: [],
      nowTime: "",
      form:{},
      roles:[],
      shopJson:""
    }
  },
  created() {
    //分页查询
    const userDate = localStorage.getItem("user");
    this.userId = JSON.parse(userDate).id;
    this.query();
  },
  reset() {
    this.username = "";
    this.email = "";
    this.address = "";
    this.query();
  },
  methods: {
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
      this.request.get("/order/myOrder",{
        params:{
          userId:this.userId,
        }}).then(res=>{
        console.log(res.data)
        this.tableData = res.data;
        this.total=res.data.total;
      })
      this.request.get("/role").then(res=>{
        this.roles=res.data;
      })
    },
    handleDelete(id){
      this.$confirm('此操作将申请退款, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.delete("/order/"+id).then(res=>{
          if(res.code==='200'){
            this.$message({
              message: '申请成功',
              type: 'success'
            });
            this.query();
          }else {
            this.$message.error('申请失败');
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    deleteBatch() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '请至少选择一条数据',
          type: 'warning'
        });
        return;
      }
      this.$confirm('此操作将永久删除这些配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = [];
        this.multipleSelection.forEach(item => {
          ids.push(item.id);
        })
        console.log(ids);
        this.request.post("/shopcar/deleteBatch", ids).then(res => {
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
    handleEdit(row){
      this.ModelId = row.id;
      this.request.get("/order/"+this.ModelId).then(res=>{
        this.oneTableData = res.data;
        console.log(this.oneTableData)
      })
      this.dialogFormVisible = true;
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

  },
}
</script>

<style scoped>

</style>