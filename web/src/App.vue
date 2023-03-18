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
  // setup(){
  //   $.ajax({
  //     url: "http://localhost:3000/user/account/token/",
  //     type: "post",
  //     data: {
  //       botName:'bot1',
  //       botPwd:'bot1'
  //     },
  //     success(resp){
  //       console.log(resp);
  //     },
  //     error(resp){
  //       console.log("EEEEEEEEEEEEEEEEError1");
  //     }
  //   });

  //   $.ajax({
  //     url: "http://localhost:3000/user/account/info/",
  //     type: "get",
  //     headers:{
  //       Authorization : "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNTM3MDQzMjI0OWY0MDEzYmFiOWI4NGQ0YmYyNTlhMCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY3ODM0OTA5MiwiZXhwIjoxNjc5NTU4NjkyfQ.Ht3rPxhC5gh92rdP_AVGFCYVI43ru1jcmWO6NbpyVV0"
  //     },
  //     success(resp){
  //       console.log(resp);
  //     },
  //     error(resp){
  //       console.log("EEEEEEEEEEEEEEEEEError2");
  //     }
  //   });

  //   $.ajax({
  //     url: "http://localhost:3000/user/account/register/",
  //     type: "post",
  //     data: {
  //       botName : 'bot4',
  //       botPwd : 'bot4',
  //       confirmedPwd : 'bot4'
  //     },
  //     success(resp){
  //       console.log(resp);
  //     },
  //     error(resp){
  //       console.log("EEEEEEEEEEEEEEEEEError3");
  //     }
  //   });
  
}
</script>

<style>   
  body{
    /* @表示根目录 */
    background-image: url("@/assets/images/background.jpg");
    background-size: auto;
  }
</style>


