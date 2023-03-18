<template>
    <div ref="parent" class="gamemap">
        <!-- 游戏地图画到canvas里边,canvas的用法可以在mdn中搜canvas   tabindex表示获取用户输入 -->
        <canvas ref="canvas" tabindex="0"></canvas>
    </div>
</template>

<script>
import { GameMap } from "@/assets/scripts/GameMap.js"
import { ref, onMounted } from "vue";
import { useStore } from "vuex";

export default{
    setup(){
        const store = useStore();
        let parent = ref(null);
        let canvas = ref(null);
        // 当组件加载完后执行onMounted
        onMounted(() =>{
            store.commit("updateGameObjcet", new GameMap(canvas.value.getContext('2d'),parent.value,store));
        });
        return{
            parent,
            canvas
        }
    }
}
</script>



<style scoped>
div.gamemap{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>



















