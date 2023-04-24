<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary" style="background-color:wheat;">
        <div class="container-fluid">
            <router-link :to="{name : 'home'}" class="navbar-brand">KingOfBot</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <router-link :to="{name : 'pk_index'}" class="nav-link">对战</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{name : 'record_index'}" class="nav-link">对局列表</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{name : 'ranklist_index'}" class="nav-link">排行榜</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{name : 'shopping_botshop'}" class="nav-link">商店</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{name : 'leavemessage_roast'}" class="nav-link">吐槽</router-link>
                    </li>
                    <li class="nav-item" v-if="store.state.user.token === ''">
                        <router-link :to="{name : 'user_account_login'}" class="nav-link">登录</router-link>
                    </li>
                    <li class="nav-item" v-if="store.state.user.token === ''">
                        <router-link :to="{name : 'user_account_register'}" class="nav-link">注册</router-link>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            我的
                        </a>
                        <ul class="dropdown-menu" style="background-color:wheat;">
                            <router-link :to="{name : 'user_bot_index'}" class="dropdown-item">我的Bot</router-link>
                            <a href="#" data-bs-toggle="modal" data-bs-target="#put-bot-btn" class="dropdown-item" @click="refresh_subbots()">上传bot</a>
                            <router-link :to="{name : 'shopping_cart'}" class="dropdown-item">购物车</router-link>
                            <router-link :to="{name : 'user_account_KOB'}" class="dropdown-item">我的账户</router-link>
                            <router-link :to="{name : 'home'}" @click="logout" class="dropdown-item">退出</router-link>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="modal fade" id="put-bot-btn" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="model-head">
                    <div style="margin-top: 20px; margin-left: 20px;">上传Bot</div>
                </div>
                <div class="modal-body">
                    <select class="form-select" aria-label="Default select example" v-model="select_subbot">
                        <option v-for="subbot in subbots" :key="subbot.id" :value="subbot.id">{{ subbot.subbotName }}</option>
                    </select>
                    <div class="mb-3">
                        <label for="price" class="form-label" style="color: black;">建议subbot零售价(Ko币)</label>
                        <input v-model="price" type="text" class="form-control" id="price" placeholder="建议subbot零售价">
                    </div>
                </div>
                <div class="modal-footer">
                    <div>{{ message }}</div>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" @click="upLodaBot()">上传</button>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import { useStore } from 'vuex';
import { ref } from "vue";
import $ from "jquery";

export default{
    setup(){
        const store = useStore();
        const route = useRoute();
        let route_name = computed(() => route.name);
        let subbots = ref([]);
        let select_subbot = ref("-1");
        let message = ref("");
        let price = ref("100");

        const refresh_subbots = () => {
            $.ajax({
                url : "http://localhost:3000/api/user/subuser/querylist/",
                type : "get",
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    subbots.value = resp;
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        const upLodaBot = () => {
            $.ajax({
                url : "http://localhost:3000/api/shopping/botshopadd/",
                type : "get",
                data : {
                    id : select_subbot.value,
                    price : price.value,
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    message.value = "上传成功";
                },
                error(resp){
                    message.value = "上传失败";
                    console.log(resp);
                }
            })
        }
        
        

        const logout = () => {
            store.dispatch("logout");
        }

        return {
            route_name,
            logout,
            subbots,
            select_subbot,
            message,
            upLodaBot,
            price,
            store,
        }
    }
}
</script>




<!-- 当 style 标签有 scoped 属性时，它的 CSS 只作用于当前组件中的元素 -->
<style scoped>
    @import url("http://cndjs.couldflare.com/ajax/libs/font-awsome/5.15.1/scc/all.min.css");
</style>











