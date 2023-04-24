<template>
    <div class="col-10">
        <div style="margin-top: 15px;margin-left: 250px;">
            <div v-for="botshop in botsShop" :key="botshop.id" style="display: inline-block; border:solid 1px greenyellow">
               <div class="one-objcet">
                    <img src="@/assets/images/paimeng.jpg" class="photo">
                    <a :href="'http://localhost:8080/shopping/object/' + botshop.id + '/'" style="display: inline-block;text-decoration: none;">bot名称:{{ botshop.name }}  价格:{{ botshop.price }}  库存:{{ botshop.stock }}</a>
                    <a href="#" style="display: inline-block;text-decoration: none;" @click="add_to_cart(botshop.id)">加入购物车</a>
                </div>
            </div>
        </div>

        <nav aria-label="...">
            <ul class="pagination" style="float: right;">
                <li class="page-item">
                    <a class="page-link" href="#" @click="click_page(-2)">上一页</a>
                </li>
                <li v-for="page in pages" :key="page.number" :class="'page-item ' + page.is_active">
                    <a class="page-link" href="#" @click="click_page(page.number)">{{ page.number }}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="#" @click="click_page(-1)">下一页</a>
                </li>
            </ul>
        </nav>
    </div>
</template>


<script>
import Common from "@/components/common.vue"
import { useStore } from "vuex"
import $ from "jquery"
import { ref } from "vue"
import router from "@/router/index"

export default{
    components : {
        Common,
    },

    setup(){
        const store = useStore();
        let current_page = 1;
        let botsShop = ref([]);
        let total_botsShop = 1;
        let pages = ref();

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url : "http://localhost:3000/api/shopping/botshop/",
                data : {
                    page,
                },
                type : "get",
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    botsShop.value = resp.botsShop;
                    total_botsShop = resp.botsShop_cnt;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        
        pull_page(current_page);

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_botsShop / 6));
            let new_pages = [];
            for(let i = current_page - 2; i <= current_page + 2; i++){
                if(i >= 1 && i <= max_pages){
                    new_pages.push({
                        number : i,
                        is_active : i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const click_page = page => {
            if(page === -2) page = current_page - 1;
            else if(page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_botsShop / 6));
            if(page >= 1 && page <= max_pages){
                pull_page(page);
            }
        }

        const add_to_cart = id => {
            store.commit("addShoptoCart", parseInt(id));
            localStorage.setItem("array",store.state.user.shopcartarray);
        }

        return { 
            botsShop,
            click_page,
            pages,
            add_to_cart,
        }
    }
}
</script>


<style scoped>
.one-objcet{
    height: 30vh;
    width: 20vw;
    display: inline-block;
    margin-left: 20px;
    margin-bottom: 20px;
}

.one-objcet > a{
    position: relative;
    text-decoration: none;
    margin-left: 2vw;
    
}

.photo{
    height: 25vh;
    width: 18vw;
    margin-left: 1vw;
    margin-top: 2vh;
    margin-bottom: 2vh;
    border-radius: 50%;
}
</style>










