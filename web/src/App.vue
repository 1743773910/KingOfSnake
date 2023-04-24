<template>
  <NavBar />
  <router-view></router-view>
</template>


<script>
import NavBar from "@/components/NavBar.vue"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import $ from "jquery"


// export defaul:在html页面上导出这段代码，即在html页面上执行这段代码
export default{
  components :{
    NavBar
  },
}
</script>

<style>   
  body{
    /* @表示根目录 */
    background-image: url("@/assets/images/background.jpg");
    background-size: auto;
  }
</style>


