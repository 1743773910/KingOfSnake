import { AcGameObject } from "./AcGameObject";
import { Wall } from "@/assets/scripts/Wall";
import { Snake } from "@/assets/scripts/Snake";


export class GameMap extends AcGameObject{
    constructor(ctx, parent, store){
        // ctx为画布， parent用来动态修改画布长宽
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0; // 每个正方形小格子的最大边长，用于画布

        this.row = 25;
        this.col = 16;
        this.walls = [];
        this.wallcnt = 30;

        this.snakes = [
            new Snake({id : 0, color:"#4876EC", r : this.row - 2, c : 1}, this),
            new Snake({id : 1, color:"#F94848", r : 1, c : this.col - 2}, this),
        ]
    }

    create_walls(){
        const g = this.store.state.pk.gamemap;
        for(let r = 0; r < this.row; r++){
            for(let c = 0; c < this.col; c++){
                if(g[r][c]){
                    this.walls.push(new Wall(r,c, this));
                }
            }
        }
        return true;
    }

    cheak_valid(cell){
        // 检测目标位置是否合法,没有撞到两条蛇的身体和障碍物
        for(const wall of this.walls){
            if(wall.r === cell.r && wall.c === cell.c){
                return false;
            }
        }
        for(const snake of this.snakes){
            let k = snake.cells.length;
            if(!snake.cheak_tail_increasing()){
                // 蛇尾可以走
                k--;
            }
            for(let i = 0; i < k; i++){
                if(snake.cells[i].r === cell.r && snake.cells[i].c === cell.c) return false;
            }
            
        }
        return true;
    }

    start(){
        this.create_walls();
        this.add_listening_events()
    }


    add_listening_events(){
        if(this.store.state.record.is_record){
            // 如果是录像
            // 每300ms执行一遍
            let k = 0;
            const a_steps = this.store.state.record.a_steps;
            const b_steps = this.store.state.record.b_steps;
            const loser = this.store.state.record.record_loser;
            const [snake0, snake1] = this.snakes;
            const interval_id = setInterval(() => {
                if(k >= a_steps.length - 1){
                    // 已经结束
                    if(loser === "all" || loser === "A"){
                        snake0.status = "die";
                    }
                    if(loser === "all" || loser === "B"){
                        snake1.status = "die";
                    }
                    clearInterval(interval_id);
                } else {
                    snake0.set_direction(parseInt(a_steps[k]));
                    snake1.set_direction(parseInt(b_steps[k]));
                }
                k++;
            }, 300);

        } else {
             // 获取用户输入
            this.ctx.canvas.focus();
            this.ctx.canvas.addEventListener("keydown", e => {
                let d = -1;
                if(e.key === 'w') d = 0;
                else if(e.key === 'd') d = 1;
                else if(e.key === 's') d = 2;
                else if(e.key === 'a') d = 3;

                if(d >= 0){
                    this.store.state.pk.socket.send(JSON.stringify({
                        event : "move",
                        direction : d,
                    }));
                }
            });
        }

       
    }

    update_size(){
        this.L = parseInt(Math.min(this.parent.clientWidth / this.col, this.parent.clientHeight / this.row));
        this.ctx.canvas.width = this.L * this.col;
        this.ctx.canvas.height = this.L * this.row;
    }

    next_step(){
        // 让两条蛇进入下一回合
        for(const snake of this.snakes){
            snake.next_step();
        }
    }
    cheak_ready(){
        // 判断两条蛇是否都准备好下一回合了
        for(const snake of this.snakes){
            if(snake.status !== "idle") return false;
            if(snake.direction === -1) return false;
        }
        return true;
    }

    update(){
        this.update_size();
        this.render();
        if(this.cheak_ready()){
            this.next_step();
        }
    }
    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let r = 0; r < this.row; r++){
            for(let c = 0; c < this.col; c++){
                if((r + c) % 2 == 0){
                    this.ctx.fillStyle = color_even;
                }else{
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}


















