import $ from 'jquery'

export default{
    state: {
        id:"",
        botName:"",
        photo:"",
        token:"",
        is_login:false,
        pulling_info : true, // 是否正在从云端拉取信息
    },
    getters: {
    },
    mutations: {
        updateUser(state, user){
            state.id = user.id;
            state.botName = user.botName;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token){
            state.token = token;
        },
        logout(state){
            state.id='',
            state.botName='',
            state.photo='',
            state.is_login=false,
            state.token='';
        },
        updatePullingInfo(state, pulling_info){
            state.pulling_info = pulling_info;
        }
    },
    actions: {
        login(context, data){
            $.ajax({
                url: "http://localhost:3000/user/account/token/",
                type: "post",
                data: {
                    botName:data.botName,
                    botPwd:data.botPwd,
                },
                success(resp){
                    if(resp.message === 'success'){
                        localStorage.setItem("jwt_token", resp.token); // 进行登录持久化
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    }else{
                        data.error(resp);
                    }
                },
                error(resp){
                    data.error(resp);
                }
            });
        },

        getinfo(context, data){
            $.ajax({
                url: "http://localhost:3000/user/account/info/",
                type: "get",
                headers:{
                    Authorization : "Bearer " + context.state.token,
                },
                success(resp){
                    if(resp.message === 'success'){
                        context.commit("updateUser", {
                            ...resp,
                            is_login:true,
                        })
                        data.success(resp);
                    }else{
                        data.error(resp);
                    }
                },
                error(resp){
                    data.message(resp);
                }
            });
        },

        logout(context){
            localStorage.removeItem("jwt_token");
            context.commit("logout");
        }
    },
    modules: {
    }
}