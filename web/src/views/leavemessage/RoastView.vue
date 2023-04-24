<template>
    <div class="col-8" style="margin-left: 260px;">
        <div style="overflow-y: auto; margin-top: 40px;height: 500px;background-color: aliceblue;">
            <a class="fs-6" style="margin-left: 10px;" href="#" @click="click_more()">点击加载更多消息</a>
            <p class="fs-5" style="margin-left: 10px;" v-for="roastitem,index in roastitems" :key="index">
                {{ roastitem.time }}  {{ roastitem.name }} : {{ roastitem.content }}
            </p>
        </div>

        <form @submit.prevent="post">
            <div style="margin-top: 30px;">
                <input v-model="content" type="text" class="form-control" id="roast" placeholder="尽情吐槽,释放压力" style="width: 91%;height: 50px;display: inline-block;">
                <button type="submit" class="btn btn-primary" style="display: inline-block;height: 45px;width: 100px;">发送</button>
            </div>
        </form>
    </div>
</template>

<script>
import { ref } from "vue";
import { useStore } from "vuex";
import $ from "jquery";

export default{
    setup(){
        const store = useStore();
        let roastitems = ref([]);
        let number = ref(1);
        let content = ref("");
        let name = store.state.user.botName;
        

        const getroast = () =>{
            $.ajax({
                url : "http://localhost:3000/api/leavemessage/getroast/",
                type : "get",
                data : {number : number.value},
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    roastitems.value = resp.roastitems;
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        getroast();
        const post = () => {
            $.ajax({
                url : "http://localhost:3000/api/leavemessage/postroast/",
                type : "get",
                data : {
                    name : name,
                    content : content.value,
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    getroast();
                },
            })
        }
        const click_more = () => {
            number.value = parseInt(number.value) + 1;
            getroast();
        }
        return{
            getroast,
            roastitems,
            number,
            post,
            content,
            click_more,
        }
    }

}
</script>


<style scoped>

</style>


