<template calss="body">
    <div class="menu-bar">
        <ul>
            <!-- 用相对路径,即localhost:8080 + path -->
            <li><router-link :to="{name : 'home'}">KingOfBot</router-link></li>
            <li><router-link :to="{name : 'pk_index'}">对战</router-link></li>
            <li><router-link :to="{name : 'record_index'}">对局列表</router-link></li>
            <li><router-link :to="{name : 'ranklist_index'}">排行榜</router-link></li>
            <li><router-link :to="{name : 'user_bot_index'}">我的Bot</router-link>
                <div class="dropdown-menu">
                    <ul>
                        <li><router-link :to="{name : 'user_bot_index'}">下拉1</router-link></li>
                        <li><router-link :to="{name : 'user_bot_index'}">下拉2</router-link></li>
                        <li><router-link :to="{name : 'user_bot_index'}">下拉3</router-link></li>
                        <li><router-link :to="{name : 'user_bot_index'}">下拉4</router-link></li>
                    </ul>
                </div>
            </li>
            
        </ul> 
    </div>
    
</template>







<script>

</script>




<!-- 当 style 标签有 scoped 属性时，它的 CSS 只作用于当前组件中的元素 -->
<style scoped>
    @import url(http://cndjs.couldflare.com/ajax/libs/font-awsome/5.15.1/scc/all.min.css);
    @import url("@/assets/CSS/NavCss.css");
</style>











