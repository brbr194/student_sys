<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div><span style="color: deepskyblue ">{{ user.name }} </span> 欢迎您，祝您今天过得开心！</div>
    </div>

  </div>
</template>

<script setup>
  import request from "@/utils/request";
  const user = JSON.parse(localStorage.getItem('login_user') || '{}')
</script>