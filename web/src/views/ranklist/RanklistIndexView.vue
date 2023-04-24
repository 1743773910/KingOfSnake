<template>
    <div class="col-10">
        <table class="table table-dark table-striped" style="margin-top: 15px; margin-left: 150px;">
            <thead>
                <tr>
                    <th style="text-align: center;">排行</th>
                    <th style="text-align: center;">玩家</th>
                    <th style="text-align: center;">天梯分</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user,index in users"  :key="index" style="text-align: center;">
                    <td>{{ (current_page-1)*10+index+1 }}</td>
                    <td>
                        <img src="@/assets/images/paimeng.jpg" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ user.botName }}</span>
                    </td>
                    <td>{{ user.botRating }}</td>
                </tr>
            </tbody>
        </table>
        
        <div style="text-align:right;margin-bottom: 10px;color: darkgrey ;">注:排行榜每隔5分钟更新一次</div>

        <nav aria-label="...">
            <ul class="pagination" style="float: right;">
                <li class="page-item">
                    <a class="page-link" @click="click_page(-2)" href="#">上一页</a>
                </li>
                <li v-for="page in pages" :key="page.number" :class="'page-item ' + page.is_active" @click="click_page(page.number)">
                    <a class="page-link" href="#">{{ page.number }}</a>
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
import { useStore } from "vuex";
import { ref } from "vue";
import $ from "jquery";

export default{
    components: {
        
    },

    setup() {
        const store = useStore();
        let current_page = ref(1);
        let users = ref([]);
        let total_users = 0;
        let pages = ref([]);

        // =page表示传过来一个page
        const pull_page = page => {
            current_page.value = page;
            $.ajax({
                url : "http://localhost:3000/api/ranklist/getlist/",
                data : {
                    page,
                },
                type : "get",
                headers: {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    users.value = resp.users;
                    total_users = resp.users_cnt;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        pull_page(1);
        setInterval(()=>{
            pull_page(current_page.value);
        }, 1000*60*5);


        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users / 10));
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
            let max_pages = parseInt(Math.ceil(total_users / 10));
            if(page >= 1 && page <= max_pages){
                pull_page(page);
            }
        }

        return {
            users,
            pages,
            click_page,
            current_page,
        }
    }
}
</script>

<style scoped>
.record-user-photo {
    height: 5vh;
    width: 5vh;
    border-radius: 50%;
}

</style>