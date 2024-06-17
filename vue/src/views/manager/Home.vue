<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div><span style="color: deepskyblue ">{{ user.name }} </span> 欢迎您，祝您今天过得开心！</div>
    </div>

  </div>
</template>

<script setup>
  import request from "@/utils/request";
  import {ElMessage} from "element-plus";

  const user = JSON.parse(localStorage.getItem('login_user') || '{}')
  console.log(user)
  // 检查 user 是否为空对象
  if (Object.keys(user).length === 0) {
    // 如果 user 是空对象，显示错误消息
    ElMessage.error("无token，请先登录！！！");
  }
</script>