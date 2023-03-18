<template>
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 4.1上
  <NavBar />
  <router-view></router-view>
</template>


<script>
import NavBar from "@/components/NavBar.vue"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

// export defaul:在html页面上导出这段代码，即在html页面上执行这段代码
export default{
  components :{
    NavBar
  }
}
</script>

<style>
  body{
    /* @表示根目录 */
    background-image: url("@/assets/images/background.jpg");
    background-size: auto;
  }
</style>


<<<<<<< HEAD
=======
=======
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
>>>>>>> 4.1上
>>>>>>> 4.1上
