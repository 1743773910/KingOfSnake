<template>
    <Common v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="botName" class="form-label">用户名</label>
                        <input v-model="botName" type="text" class="form-control" id="botName" placeholder="用户名">
                    </div>
                    <div class="mb-3">
                        <label for="botPwd" class="form-label">密码</label>
                        <input v-model="botPwd" type="password" class="form-control" id="botPwd" placeholder="密码">
                    </div>
                    <div class="message">{{ message }}</div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
            </div>
        </div>
    </Common>
</template>

<script>
import Common from "@/components/common.vue"
import { useStore } from "vuex";
import { ref } from 'vue'
import router from "@/router/index";
import { flip } from "@popperjs/core";

export default{
    components: {
        Common
    },
    setup() {
        const store = useStore();
        let botName = ref("");
        let botPwd = ref("");
        let message = ref("");

        const jwt_token = localStorage.getItem("jwt_token");
        if(jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success(){
                    router.push({name : "home"});
                    store.commit("updatePullingInfo",false);
                },
                error(){
                    store.commit("updatePullingInfo",false);
                }
            })
        }else{
            store.commit("updatePullingInfo",false);
        }

        const login = () =>{
            message.value = "";
            store.dispatch("login", {
                botName : botName.value,
                botPwd : botPwd.value,
                success(){
                    // 获取用户信息
                    store.dispatch("getinfo", {
                        success(){
                            router.push({name : "home"}); // 登录成功页面跳转
                        }
                    })
                },
                error(resp){
                    message.value = "用户名或密码错误";
                    console.log(resp);
                }   
            })
        }
        return {
            botName,
            botPwd,
            message,
            login,
        }
    }
}
</script>

<style scoped>
.btn{
    width: 100%;
}
.message{
    color: red;
}
</style>