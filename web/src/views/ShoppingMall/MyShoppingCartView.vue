<template>
    <div class="col-10">
        <table class="table table-dark table-striped" style="margin-top: 15px; margin-left: 120px;">
            <thead>
                <tr>
                    <th style="text-align: center;">商品编号</th>
                    <th style="text-align: center;">商品</th>
                    <th style="text-align: center;">商品昵称</th>
                    <th style="text-align: center;">商品单价</th>
                    <th style="text-align: center;">商品总价</th>
                    <th style="text-align: center;">购买数量</th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="shopitem,index in shopitems" :key="index" style="text-align: center;">
                    <td>{{ shopitem.id }}</td>
                    <!-- <td class="photo">{{ shopitem.photo }}</td> -->
                    <td><img src="@/assets/images/paimeng.jpg" class="photo"></td>
                    <td>{{ shopitem.name }}</td>
                    <td>{{ shopitem.price }}</td>
                    <td>{{ shopitem.price * store.state.user.shopcartarraycnt[(current_page-1)*5+index] }}</td>
                    <td>
                        <a href="#" @click="click_dec((current_page-1)*5+index)" style="display: inline-block;margin-right: 25px;font-size: 200%;text-decoration: none;">-</a>
                        <input v-model="store.state.user.shopcartarraycnt[(current_page-1)*5+index]" type="text" class="form-control" id="botName" placeholder="1" style="width: 30%;display: inline-block;">
                        <a href="#"  @click="click_add((current_page-1)*5+index)" style="display: inline-block; margin-left: 20px;font-size: 170%;text-decoration: none;">+</a>
                    </td>
                </tr>
            </tbody>
        </table>

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
import { useStore } from "vuex"
import { ref } from "vue"
import $ from "jquery"


export default{

    setup(){
        const store = useStore();
        let shopitems = ref([]);   
        let current_page = ref(1);
        let pages = ref();
        let cnt = ref(1);
        
        // localStorage.setItem("arraycnt",store.state.user.shopcartarraycnt);
        

        const getcart = page => {
            current_page.value = page;
            $.ajax({
                url : "http://localhost:3000/api/shopping/getcart/",
                type : "get",
                data : {
                    ids : localStorage.getItem("array"),
                    page : page,
                    number : "5",
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    shopitems.value = resp.shopitems;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        getcart(current_page.value);


        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(store.state.user.shopcartarray.length / 5));
            let new_pages = [];
            for(let i = current_page.value - 2; i <= current_page.value + 2; i++){
                if(i >= 1 && i <= max_pages){
                    new_pages.push({
                        number : i,
                        is_active : i === current_page.value ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const click_page = page => {
            if(page === -2) page = current_page.value - 1;
            else if(page === -1) page = current_page.value + 1;
            let max_pages = parseInt(Math.ceil(store.state.user.shopcartarray.length / 5));
            if(page >= 1 && page <= max_pages){
                getcart(page);
            }
        }

        const click_add = (index) => {
            store.state.user.shopcartarraycnt[index] = parseInt(store.state.user.shopcartarraycnt[index]) + 1;
        }

        const click_dec = (index) => {
            store.state.user.shopcartarraycnt[index] = parseInt(store.state.user.shopcartarraycnt[index]) - 1;
            if(store.state.user.shopcartarraycnt[index] < 0) store.state.user.shopcartarraycnt[index] = 0;
        }
        return {
            shopitems,
            click_page,
            pages,
            cnt,
            store,
            current_page,
            click_add,
            click_dec,
        }
    }
}


</script>

<style scoped>
.photo {
    height: 5vh;
    width: 5vh;
    border-radius: 50%;
}
</style>







