<template>
<div>
    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
  <el-menu-item index="/shopHome">
    <template slot="title">
      <i class="el-icon-house"></i>
      <span slot="title">主页</span>
    </template>
  </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item index="/user">
        <template slot="title">
          <i class="el-icon-user"></i>
          <span slot="title">用户管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/role">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span slot="title">角色管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/menu">
        <template slot="title">
          <i class="el-icon-s-grid"></i>
          <span slot="title">菜单管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/file">
        <template slot="title">
          <i class="el-icon-document"></i>
          <span slot="title">文件管理</span>
        </template>
      </el-menu-item>
    </el-submenu>
  <el-submenu index="3">
    <template slot="title">
      <i class="el-icon-right"></i>
      <span slot="title">开始装机</span>
    </template>
    <el-menu-item index="/mainBoard">
      <template slot="title">
        <i class="el-icon-bank-card"></i>
        <span slot="title">主板</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/graphics">
      <template slot="title">
        <i class="el-icon-c-scale-to-original"></i>
        <span slot="title">显卡</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/cpu">
      <template slot="title">
        <i class="el-icon-cpu"></i>
        <span slot="title">处理器</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/memorySticks">
      <template slot="title">
        <i class="el-icon-collection-tag"></i>
        <span slot="title">内存条</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/hardDisk">
      <template slot="title">
        <i class="el-icon-stopwatch"></i>
        <span slot="title">硬盘</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/power">
      <template slot="title">
        <i class="el-icon-switch-button"></i>
        <span slot="title">电源</span>
      </template>
    </el-menu-item>
    <el-menu-item index="/cooling">
      <template slot="title">
        <i class="el-icon-switch-button"></i>
        <span slot="title">散热</span>
      </template>
    </el-menu-item>
  </el-submenu>
</div>
</template>

<script>
export default {
  name: "Look"
}
</script>

<style scoped>

</style>