const AC_GAME_OBJECTS = [];

export class AcGameObject{
    constructor(){ // 构造函数
        AC_GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_called_start = false;
    }

    start(){ // 创建时执行一次
    
    }
    update(){ // 每一帧执行一次
        
    }
    on_destory(){ // 删除之前执行
        
    }
    destory(){
        this.on_destory();
        for(let i in AC_GAME_OBJECTS){
            const obj = AC_GAME_OBJECTS[i];
            if(obj === this){
                AC_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}
let last_timestamp; // 上一次执行的时刻

// requestAnimationFrame()函数会在浏览器刷新时(浏览器1秒刷新60次)执行一遍括号里的内容
const step = timestamp =>{
    for(let obj of AC_GAME_OBJECTS){
        if(!obj.has_called_start){
            obj.has_called_start = true;
            obj.start();
        }else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step);
}
requestAnimationFrame(step);





