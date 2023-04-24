import $ from 'jquery'

export default{
    state: {
        id:"",
        botName:"",
        photo:"",
        token:"",
        phone:"",
        shopcartarray:[],
        shopcartarraycnt:[],
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
        },
        updatePhone(state,phone){
            state.phone = phone;
        },
        addShoptoCart(state,number){
            for(let i = 0; i < state.shopcartarray.length; i++){
                if(state.shopcartarray[i] === number) return;
            }
            state.shopcartarray.push(number);
            state.shopcartarraycnt.push(1);
            localStorage.setItem("arraycnt", state.shopcartarraycnt);
        },
        delShopFromCart(state, number){
            for(let i = 0; i < state.shopcartarray.length; i++){
                if(state.shopcartarray[i] === number){
                    state.shopcartarray.splice(i,1);
                    state.shopcartarraycnt.splice(i,1);
                }
            }
        },
        addShoptoCartCnt(state,data){
            for(let i = 0; i < state.shopcartarray.length; i++){
                if(state.shopcartarray[i] === data.number){
                    state.shopcartarraycnt[i] = data.cnt;
                }
            }
        },
    },
    actions: {
        
        login(context, data){
            $.ajax({
                url: "http://localhost:3000/api/user/account/token/",
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

        loginByPhone(context, data){
            $.ajax({
                url: "http://localhost:3000/api/user/account/tokenByPhone/",
                type : "post",
                data : {
                    phone : data.phone,
                    code : data.code,
                    method : data.method,
                },
                success(resp){
                    if(resp.message === 'success'){
                        localStorage.setItem("jwt_token", resp.token);
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

        sendCode(context, data){
            $.ajax({
                url: "http://localhost:3000/api/user/account/tokenByPhone/",
                type : "post",
                data : {
                    phone : data.phone,
                    code : data.code,
                    method : data.method,
                },
                success(resp){
                    if(resp.message === 'success'){
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
                url: "http://localhost:3000/api/user/account/info/",
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