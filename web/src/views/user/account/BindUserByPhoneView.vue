<template>
    <Common v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center">
            <div class="col-3">
                <button type="submit" class="btn btn-primary" @click="btn01clicked">已有用户</button>
                <button type="submit" class="btn btn-primary" style="margin-top: 50px;" @click="btn02clicked">注册用户</button>
            </div>
        </div>
    </Common>
</template>


<script>
import Common from "@/components/common.vue"
import router from "@/router/index"

export default{
    components : {
        Common,
    },
    setup(){
        const btn01clicked = () => {
            router.push({name : "user_account_login"});
        }
        const btn02clicked = () => {
            router.push({name : "user_account_register"});
        }
        return {
            btn01clicked,
            btn02clicked,
        }
    }
    
}
</script>


<style scoped>
.btn{
    width: 100%;
}
</style>






