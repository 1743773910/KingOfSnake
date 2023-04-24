<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img src="@/assets/images/paimeng.jpg" alt="" style="height: 26vh; width: 70%;">
                    <div class="user-username">{{ $store.state.user.botName }}</div>
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select class="form-select" aria-label="Default select example" v-model="select_subbot">
                        <option selected value="-1">亲自上阵</option>
                        <option v-for="subbot in subbots" :key="subbot.id" :value="subbot.id">{{ subbot.subbotName }}</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img src="@/assets/images/paimeng.jpg" alt="" style="height: 26vh; width: 70%;">
                    <div class="user-username">{{ $store.state.pk.opponent_username }}</div>
                </div>
            </div>
            <div class="row-12" style="text-align: center; padding-top: 15vh;">
                <button type="button" class="btn btn-success btn-lg" @click="click_match_btn">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>


<script>
import { ref } from "vue"
import { useStore } from "vuex"
import $ from "jquery"


export default{
    components :{
        
    },

    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");
        let subbots = ref([]);
        let select_subbot = ref("-1");

        const click_match_btn = () => {
            if(match_btn_info.value === "开始匹配"){
                match_btn_info.value = "取消";
                store.state.pk.socket.send(JSON.stringify({
                    event : "start-matching",
                    subbot_id : select_subbot.value,
                }));
            }else{
                match_btn_info.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event : "stop-matching",
                }));
            }
        }

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
        refresh_subbots();

        return {
            match_btn_info,
            click_match_btn,
            subbots,
            select_subbot,
        }
    }
}
</script>


<style scoped>
.matchground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(50,50,50,0.5);
}
.user-photo{
    padding-top: 10vh;
    text-align: center;
}
.user-photo > img {
    border-radius: 50%;
}
.user-username{
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color:white;
    padding-top: 2vh;
}
.user-select-bot{
    padding-top: 10vh;
}
.user-select-bot > select{
    width: 60%;
    margin: 0 auto;
}
</style>




