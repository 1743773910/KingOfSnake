<template>
    <div class="menu-bar">
        <ul>
            <li><router-link :to="{name : 'home'}">KingOfBot</router-link></li>
            <li><router-link :to="{name : 'pk_index'}">对战</router-link></li>
            <li><router-link :to="{name : 'record_index'}">对局列表</router-link></li>
            <li><router-link :to="{name : 'ranklist_index'}">排行榜</router-link></li>
            <ul v-if="$store.state.user.is_login">
                <li><router-link :to="{name : 'user_bot_index'}">我的Bot</router-link>
                    <div class="dropdown-menu">
                        <ul>
                            <li><router-link :to="{name : 'user_bot_index'}">下拉1</router-link></li>
                            <li><router-link :to="{name : 'user_bot_index'}">下拉2</router-link></li>
                            <li><router-link :to="{name : 'user_bot_index'}">下拉3</router-link></li>
                            <li><a href="http://localhost:8080" @click="logout">退出</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
            <ul v-else-if="!$store.state.user.pulling_info">
                <li><a href="http://localhost:8080/user/account/login/">登录</a></li>
                <li><a href="http://localhost:8080/user/account/register/">注册</a></li>
            </ul> 
        </ul>
    </div>
    
</template>


<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue'
import { useStore } from 'vuex';

export default{
    setup(){
        const store = useStore();
        const route = useRoute();
        let route_name = computed(() => route.name)

        const logout = () => {
            store.dispatch("logout");
        }

        return {
            route_name,
            logout,
        }
    }
}
</script>




<!-- 当 style 标签有 scoped 属性时，它的 CSS 只作用于当前组件中的元素 -->
<style scoped>
    @import url("http://cndjs.couldflare.com/ajax/libs/font-awsome/5.15.1/scc/all.min.css");
    @import url("@/assets/CSS/NavCss.css");
</style>











