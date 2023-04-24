<template>
    <Common>
        <slot>aa</slot>
    </Common>
</template>

<script>
import Common from "@/components/common.vue"
import { ref } from "vue"
import $ from "jquery"
import { useStore } from "vuex"

export default{
    components : {
        Common,
    },

    // setup(){
    //     let id =  parseInt(location.pathname.substring(17).replace('/',''));
    //     const store = useStore();
    //     let item = ref();

    //     const queryForOne = () => {
    //         $.ajax({
    //             url : "http://localhost:3000/api/shopping/queryforone/",
    //             type : "get",
    //             data : {
    //                 id : id,
    //             },
    //             headers : {
    //                 Authorization : "Bearer " + store.state.user.token,
    //             },
    //             success(resp){
    //                 item = resp.item;
    //             },
    //             error(resp){
    //                 console.log(resp);
    //             }
    //         })
    //     }

    //     return{
    //         queryForOne,
    //         item,
    //     }
        
    // }
}
</script>

<style scoped>

</style>





