<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            Draw
        </div>
        <!-- 两个等号表示不比较类型，只转成字符串比较，此处user_id是字符串，a_id是数字 -->
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id == $store.state.user.id">
            Lose
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id == $store.state.user.id">
            Lose
        </div>
        <div class="result-board-text" v-else>
            Win
        </div>
        <div class="result-board-btn">
            <button type="button" class="btn btn-success btn-lg" @click="restart">再来一局</button>
        </div>
    </div>
</template>


<script>
import { useStore } from "vuex"

export default {
    setup() {
        const store = useStore();
        const restart = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username : "我的对手",
                photo : "@/assets/images/paimeng.jpg"
            });
        }

        return {
            restart,
        }
    }
}

</script>

<style scoped>
.result-board {
    height: 35vh;
    width: 30vw;
    background-color: rgba(50,50,50,0.5);
    position: absolute;
    top: 20vh;
    left: 68vh;
}

.result-board-text {
    text-align: center;
    color: red;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 5vh;
}

.result-board-btn {
    text-align: center;
    padding-top: 10vh;
}
</style>








