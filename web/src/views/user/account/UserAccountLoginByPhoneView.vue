<template>
    <Common v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="loginByPhone">
                    <div class="mb-3">
                        <label for="phone" class="form-label">手机号</label>
                        <input v-model="phone" type="text" class="form-control" id="phone" placeholder="手机号">
                    </div>
                    <div class="mb-3">
                        <label for="code" class="form-label">验证码</label>
                        <input v-model="code" type="password" class="form-control" id="code" placeholder="验证码">
                    </div>
                    <div class="message">{{ message }}</div>
                    <span><a href="#" @click="sendCode">发送验证码</a></span>
                    <span><router-link :to="{name : 'user_account_login'}" style="padding-left: 20px;">密码登录</router-link></span>
                    <button type="submit" class="btn btn-primary" style="margin-left: 50px;">提交</button>
                </form>
            </div>
        </div>
    </Common>
</template>




<script>
import Common from "@/components/common.vue"
import { useStore } from "vuex"
import { ref } from "vue"
import router from "@/router/index";

export default{
    components :{
        Common
    },
    setup() {
        const store = useStore();
        let phone = ref("");
        let code = ref("");
        let message = ref("");
        let method = ref("");

        const loginByPhone = () => {
            message.value = "";
            method.value = "matchingCode";
            store.dispatch("loginByPhone", {
                phone : phone.value,
                code : code.value,
                method : method.value,
                success(){
                    method.value = "";
                    store.dispatch("getinfo", {
                        success(){ 
                            router.push({name : "home"});
                        }
                    })
                },
                error(resp){
                    method.value = "";
                    if(resp.message === '验证码错误'){
                        message.value = "未输入验证码或验证码错误";
                    }else{
                        message.value = "未绑定用户";
                        store.commit("updatePhone",phone);
                        router.push({name : "user_account_bindUser"});
                    }
                }
            })
        };

        const sendCode = () => {
            message.value = "";
            method.value = "getCode";
            console.log(method.value);
            store.dispatch("sendCode", {
                phone : phone.value,
                code : code.value,
                method : method.value,
                success(){
                    message.value = "验证码发送成功";
                    method.value = "";
                },
                error(){
                    message.value = "验证码发送失败";
                    method.value = "";
                }
            })
        };

        return {
            phone,
            code,
            message,
            loginByPhone,
            sendCode,
        }
    }
}
</script>



<style scoped>
.message{
    color: red;
}
</style>



