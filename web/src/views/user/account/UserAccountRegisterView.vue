<template>
    <Common>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="botName" class="form-label">用户名</label>
                        <input v-model="botName" type="text" class="form-control" id="botName" placeholder="用户名">
                    </div>
                    <div class="mb-3">
                        <label for="botPwd" class="form-label">密码</label>
                        <input v-model="botPwd" type="password" class="form-control" id="botPwd" placeholder="密码">
                    </div>
                    <div class="mb-3">
                        <label for="confirmedPwd" class="form-label">确认密码</label>
                        <input v-model="confirmedPwd" type="password" class="form-control" id="confirmedPwd" placeholder="确认密码">
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
import { ref } from "vue"
import { useStore } from "vuex"
import router from "@/router"
import $ from 'jquery'

export default{
    components: {
        Common
    },
    setup(){
        const store = useStore();
        let botName = ref("");
        let botPwd = ref("");
        let confirmedPwd = ref("");
        let message = ref("");
        
        const register = () => {
            message.value = "";
            $.ajax({
                url : "http://localhost:3000/user/account/register/",
                type : "post",
                data : {
                    botName : botName.value,
                    botPwd : botPwd.value,
                    confirmedPwd : confirmedPwd.value,
                },
                success(resp) {
                    if(resp.message === "success") {
                        router.push({name : "user_account_login"});
                    }else{
                        message.value = resp.message;
                    }
                },
                erroor(resp){
                    message.value = resp.message;
                }
            });
        }

        return {
            botName,
            botPwd,
            confirmedPwd,
            message,
            register
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