<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img src="@/assets/images/paimeng.jpg" alt="" style="width: 100%; height: 230px;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px; background-color:slategray;">
                    <div class="card-header">
                        <span style="font-size: 140%;">我的Bot</span>
                        <button type="button" class="btn btn-primary" style="float:right;" data-bs-toggle="modal" data-bs-target="#add-bot-btn">创建bot</button>
                    </div>


                    <!-- Modal -->
                    <div class="modal fade" id="add-bot-btn" tabindex="-1">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5">创建bot</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="add-bot-subbotName" class="form-label">名称</label>
                                        <input v-model="botadd.subbotName" type="text" class="form-control" id="add-bot-subbotName" placeholder="请输入subbot昵称">
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-description" class="form-label">subbot简介</label>
                                        <textarea v-model="botadd.description" class="form-control" id="add-bot-description" rows="3" placeholder="请填写subbot简介"></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-content" class="form-label">代码</label>
                                        <VAceEditor
                                            v-model:value="botadd.content"
                                            @init="editorInit"
                                            lang="c_cpp"
                                            theme="textmate"
                                            style="height : 300px" 
                                        />
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <div class="message">{{ botadd.message }}</div>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                    <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                </div>
                            </div>
                        </div>
                    </div>




                    <div class="cart-body">
                        <table class="table table-dark table-striped">
                            <thead>
                                <tr>
                                    <th>Bot名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="subbot in subbots"  :key="subbot.id">
                                    <td>{{ subbot.subbotName }}</td>
                                    <td>{{ subbot.createTime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-primary" style="width: 25%; margin-right: 10px;" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal'+subbot.id">修改</button>
                                        <button type="button" class="btn btn-primary" style="width: 25%;" @click="remove_bot(subbot.id)">删除</button>
                                    
                                        
                                        <div class="modal fade" :id="'update-bot-modal'+subbot.id" tabindex="-1">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5" style="color: black;">修改bot</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-subbotName" class="form-label" style="color: black;">名称</label>
                                                            <input v-model="subbot.subbotName" type="text" class="form-control" id="add-bot-subbotName" placeholder="请输入subbot昵称">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-description" class="form-label" style="color: black;">subbot简介</label>
                                                            <textarea v-model="subbot.description" class="form-control" id="add-bot-description" rows="3" placeholder="请填写subbot简介"></textarea>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-content" class="form-label" style="color: black;">代码</label>
                                                            <VAceEditor
                                                                v-model:value="subbot.content"
                                                                @init="editorInit"
                                                                lang="c_cpp"
                                                                theme="textmate"
                                                                style="height : 300px" 
                                                            />
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="message">{{ subbot.message }}</div>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                        <button type="button" class="btn btn-primary" @click="update_bot(subbot)">保存修改</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { useStore } from "vuex"
import { ref,reactive } from "vue"
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from "vue3-ace-editor"
import ace from 'ace-builds'
import { init } from 'events'


export default{
    components: {
        VAceEditor
    },

    setup() {
        ace.config.set("basePath", "http://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/");
        
        const store = useStore();
        let subbots = ref([]);
        const botadd = reactive({
            subbotName : "",
            description: "",
            content : "",
            message : "",
        })

        const refresh_subbots = () => {
            $.ajax({
            url : "http://localhost:3000/user/subuser/querylist/",
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


        const add_bot = () =>{
            botadd.message = "";
            $.ajax({
                url : "http://localhost:3000/user/subuser/add/",
                type : "post",
                data : {
                    subbotName : botadd.subbotName,
                    description : botadd.description,
                    content : botadd.content,
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.message === "success"){
                        botadd.subbotName = "",
                        botadd.content = "",
                        botadd.description = "",
                        Modal.getInstance("#add-bot-btn").hide();
                        refresh_subbots();

                    }else{
                        botadd.message = resp.message;
                    }
                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        const remove_bot = (subbotid) =>{
            $.ajax({
                url : "http://localhost:3000/user/subuser/remove/",
                type : "post",
                data : {
                    subuser_id : subbotid
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.message === "success"){
                        refresh_subbots();
                    }
                }
            })
        }

        const update_bot = (subbot) =>{
            $.ajax({
                url : "http://localhost:3000/user/subuser/update/",
                type : "post",
                data : {
                    subuser_id : subbot.id,
                    subbotName : subbot.subbotName,
                    description : subbot.description,
                    content : subbot.content,
                },
                headers : {
                    Authorization : "Bearer " + store.state.user.token,
                },
                success(resp){
                    if(resp.message === "success"){
                        Modal.getInstance("#update-bot-modal" + subbot.id).hide();
                        refresh_subbots();
                    }else{
                        console.log(resp);
                    }
                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        return {
            subbots,
            botadd,
            add_bot,
            remove_bot,
            update_bot,
        }

    }
}
</script>

<style scoped>
.message{
    color: red;
}
</style>