<template>
    <div ref="parent" class="gamemap">
        <!-- 游戏地图画到canvas里边,canvas的用法可以在mdn中搜canvas   tabindex表示获取用户输入 -->
        <canvas ref="canvas" tabindex="0"></canvas>
    </div>
</template>

<script>
import { GameMap } from "@/assets/scripts/GameMap.js"
import { ref, onMounted } from "vue";
export default{
    setup(){
        let parent = ref(null);
        let canvas = ref(null);
        // 当组件加载完后执行onMounted
        onMounted(() =>{
            new GameMap(canvas.value.getContext('2d'), parent.value)
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



















